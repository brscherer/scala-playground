package com.brscherer.cartoll

import com.typesafe.config.ConfigFactory
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model.{ContentTypes, HttpEntity}
import org.apache.pekko.http.scaladsl.server.Directives.*
import org.apache.pekko.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.stringUnmarshaller
import org.apache.pekko.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContext, Future, ExecutionContextExecutor}
import scala.io.StdIn
import scala.util.{Failure, Success}
import org.json4s.*
import org.json4s.native.Serialization

import scala.collection.mutable
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api.*

import com.brscherer.cartoll.db.slick.Tables.*

case class TollModel(id: Int, date: String, carType: String, amount: Int, licensePlate: String)

object DatabaseConfig {
  private val config = ConfigFactory.load()

  def getProfile(profileName: String): JdbcProfile = {
    profileName match {
      case "postgres" => slick.jdbc.PostgresProfile
      case _ => throw new IllegalArgumentException(s"Unknown profile: $profileName")
    }
  }

  def getDbConfig(profileName: String) = {
    val profile = getProfile(profileName)
    val dbConfig = config.getConfig(s"slick.profiles.$profileName.db")
    (profile, dbConfig)
  }

}

class TollRepository(db: Database)(implicit ec: ExecutionContext) {
  def getAllTolls: Future[Seq[TollModel]] = {
    val query = Toll.result
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val action = query.map(_.map(row => TollModel(row.id, dateFormat.format(row.date), row.carType, row.amount, row.licensePlate)))
    db.run(action)
  }

  def passVehicle(carType: String): Future[Int] = {
    val amount = carType.toLowerCase match {
      case "bike" => 7
      case "car" => 13
      case "truck" => 20
      case _ => throw new IllegalArgumentException(s"Unknown car type: $carType")
    }
    val date = new java.sql.Date(System.currentTimeMillis())
    val newToll = TollRow(0, date, carType, amount, licensePlate)

    val insertAction = (Toll returning Toll.map(_.id)) += newToll
    db.run(insertAction)
  }

  def getSalesByDate(dateStr: String): Future[Seq[TollModel]] = {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val date = new java.sql.Date(dateFormat.parse(dateStr).getTime)
    val query = for {
      toll <- Toll if toll.date === date
    } yield (toll.id, toll.date, toll.carType, toll.amount, toll.licensePlate)

    db.run(query.result).map(_.map {
      case (id, date, carType, amount, licensePlate) => TollModel(id, date.toString, carType, amount, licensePlate)
    })
  }
}


trait Json4sSupport {
    private val customTypeHints = FullTypeHints(List(
      classOf[TollModel],
    ))
    implicit val formats: Formats = DefaultFormats.withHints(customTypeHints)
    implicit val serialization: Serialization.type = Serialization
}

class CarTollRoutes(tollRepository: TollRepository)(implicit ec: ExecutionContext) extends Json4sSupport {
  val route: Route =
    pathPrefix("car-toll") {
      path("list") {
        get {
          onComplete(tollRepository.getAllTolls) {
            case Success(computers) => complete(serialization.write(computers)(formats))
            case Failure(ex) => complete((500, s"An error occurred: ${ex.getMessage}"))
          }
        }
      } ~ path("charge" / Segment / Segment) { (carType, licensePlate) =>
        post {
          onComplete(tollRepository.passVehicle(carType, licensePlate)) {
            case Success(_) => complete((200, s"Vehicle $carType with license plate $licensePlate pass the toll successfully."))
            case Failure(ex) => complete((500, s"An error occurred: ${ex.getMessage}"))
          }
        }
      }
    }
}

object Main extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val (profile, dbConfig) = DatabaseConfig.getDbConfig("postgres")
  val db = Database.forConfig("", dbConfig)
  val tollRepository = new TollRepository(db)
  val routes = CarTollRoutes()
  val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes.route)

  println(s"Server now online. Please navigate to http://localhost:8080/car-toll/list\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .flatMap(_ => system.terminate())
}
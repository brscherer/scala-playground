package com.brscherer.vendingmachine

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model.{ContentTypes, HttpEntity}
import org.apache.pekko.http.scaladsl.server.Directives.*
import org.apache.pekko.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import org.apache.pekko.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.stringUnmarshaller
import org.apache.pekko.http.scaladsl.server.Route

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import org.json4s.*
import org.json4s.native.Serialization

import scala.collection.mutable
import java.time.LocalDate
import java.time.format.DateTimeFormatter


case class Item(id: String, name: String, price: Double)
case class Stock(item: Item, quantity: Int)
case class Sale(itemId: String, quantity: Int, date: LocalDate)
case class SellItemData(itemId: String, quantity: Int)

object VendingMachine {
  implicit val formats: Formats = DefaultFormats
  private val stock: mutable.Map[String, Stock] = mutable.Map(
    "1" -> Stock(Item("1", "Coke", 1.25), 10),
    "2" -> Stock(Item("2", "Pepsi", 1.50), 10),
    "3" -> Stock(Item("3", "Soda", 1.00), 10)
  )
  private val sales: mutable.ListBuffer[Sale] = mutable.ListBuffer.empty

  def addItem(item: Item, quantity: Int): Unit = {
    stock.get(item.id) match {
      case Some(existingStock) =>
        stock.update(item.id, existingStock.copy(quantity = existingStock.quantity + quantity))
      case None =>
        stock.put(item.id, Stock(item, quantity))
    }
  }

  def sellItem(itemId: String, quantity: Int): Option[Sale] = {
    stock.get(itemId) match {
      case Some(existingStock) if existingStock.quantity >= quantity =>
        stock.update(itemId, existingStock.copy(quantity = existingStock.quantity - quantity))
        val sale = Sale(itemId, quantity, LocalDate.now())
        sales += sale
        Some(sale)
      case _ => None
    }
  }

  def getSales(startDate: Option[String], endDate: Option[String]): List[Sale] = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val start = startDate.map(LocalDate.parse(_, formatter)).getOrElse(LocalDate.MIN)
    val end = endDate.map(LocalDate.parse(_, formatter)).getOrElse(LocalDate.MAX)

    sales.filter(sale => !sale.date.isBefore(start) && !sale.date.isAfter(end)).toList
  }

  def getStock: List[Stock] = stock.values.toList

  def getItemById(itemId: String): Option[Item] = stock.get(itemId).map(_.item)
}


object LocalDateSerializer extends CustomSerializer[LocalDate](format => ( {
  case JString(s) => LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE)
  case JNull => null
}, {
  case date: LocalDate => JString(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
}
))

trait Json4sSupport {
  implicit val formats: Formats = DefaultFormats + LocalDateSerializer
  implicit val serialization: Serialization.type = Serialization

  implicit def json4sUnmarshaller[A: Manifest]: FromEntityUnmarshaller[A] =
    stringUnmarshaller.map { json =>
      Serialization.read[A](json)
    }
}

class VendingMachineRoutes extends Json4sSupport {
  val route: Route =
    pathPrefix("vending-machine") {
      path("stock") {
        get {
          complete(serialization.write(VendingMachine.getStock)(formats))
        }
      } ~ path("sell-item") {
        post {
          entity(as[SellItemData]) { data =>
            VendingMachine.sellItem(data.itemId, data.quantity) match {
              case Some(sale) => complete(serialization.write(sale)(formats))
              case None => complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Item out of stock"))
            }
          }
        }
      } ~ path("sales") {
        get {
          parameters("start".?, "end".?) { (start, end) =>
            complete(serialization.write(VendingMachine.getSales(start, end))(formats))
          }
        }
      }
    }
}

object Main extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val routes = VendingMachineRoutes()
  val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes.route)

  println(s"Server now online. Please navigate to http://localhost:8080/vending-machine\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .flatMap(_ => system.terminate())
}
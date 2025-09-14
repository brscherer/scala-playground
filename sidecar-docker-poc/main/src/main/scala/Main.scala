import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.ember.server._

object Main extends IOApp {
  def run(args: List[String]) =
    EmberServerBuilder.default[IO]
      .withHost("0.0.0.0").withPort(8080)
      .withHttpApp(HttpRoutes.of[IO] {
        case GET -> Root / "ping" =>
          IO(println("[Main] Received ping")).as(Response.of[IO](Status.Ok))
      }.orNotFound)
      .build.useForever
}


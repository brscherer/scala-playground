import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Success, Failure, Random}
import ExecutionContext.Implicits.global

trait Sidecar {
  def logRequest(serviceName: String, request: String): Unit
  def logResponse(serviceName: String, response: String): Unit
}

class SidecarLogger extends Sidecar {
  override def logRequest(serviceName: String, request: String): Unit =
    println(s"[Sidecar] [$serviceName] Request sent: $request")

  override def logResponse(serviceName: String, response: String): Unit =
    println(s"[Sidecar] [$serviceName] Response received: $response")
}

object HttpClient {
  def get(url: String): Future[String] = Future {
    Thread.sleep(Random.between(100, 500)) // Simulate latency
    s"Response from $url"
  }
}

class MainService(sidecar: Sidecar) {
  val serviceName = "MainService"

  def fetchData(url: String): Future[String] = {
    sidecar.logRequest(serviceName, url)

    val response = HttpClient.get(url)

    response.onComplete {
      case Success(body) => sidecar.logResponse(serviceName, body)
      case Failure(ex)   => println(s"[Sidecar] [$serviceName] ERROR: ${ex.getMessage}")
    }

    response
  }
}

@main def runSidecarPoC(): Unit = {
  val sidecar = new SidecarLogger
  val service = new MainService(sidecar)

  val urls = List("http://api.service-a", "http://api.service-b")

  val requests = urls.map(service.fetchData)

  Await.result(Future.sequence(requests), 3.seconds)

  println("=== All requests handled ===")
}

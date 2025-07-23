import scala.concurrent.{Future, ExecutionContext}
import java.util.concurrent.Executors

// Useful in microservices where CPU usage needs to be tuned.
@main
def runCustomThreadPool(): Unit =
  val threadPool = Executors.newFixedThreadPool(2) //limits concurrency to 2 thread
  given ec: ExecutionContext = ExecutionContext.fromExecutor(threadPool)

  (1 to 5).foreach { i =>
    Future {
      println(s"[${Thread.currentThread().getName}] Running task $i")
      Thread.sleep(1000)
    }
  }

  Thread.sleep(3000)
  threadPool.shutdown()

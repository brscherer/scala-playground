import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Success, Failure}

@main
def runSimpleFuture(): Unit =
  //shared thread pool used for non-blocking tasks
  given ExecutionContext = ExecutionContext.global

  val future = Future { // runs in a different thread
    println(s"[${Thread.currentThread().getName}] Running a simple async task")
    Thread.sleep(1000)
    42
  }

  future.onComplete {
    case Success(value) => println(s"Result: $value")
    case Failure(ex)    => println(s"Failed: ${ex.getMessage}")
  }

  Thread.sleep(2000) //prevents the main thread from exiting before the future completes
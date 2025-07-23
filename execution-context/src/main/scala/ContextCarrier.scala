import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


object CorrelationContext {
  private val threadLocal = new ThreadLocal[String]()

  def set(id: String): Unit = threadLocal.set(id)
  def get(): String = threadLocal.get()
  def clear(): Unit = threadLocal.remove()
}

class CorrelationAwareExecutionContext(underlying: ExecutionContext, correlationId: String) extends ExecutionContext {
  override def execute(runnable: Runnable): Unit = {
    underlying.execute(() => {
      try {
        CorrelationContext.set(correlationId)
        runnable.run()
      } finally {
        CorrelationContext.clear()
      }
    })
  }

  override def reportFailure(cause: Throwable): Unit = {
    underlying.reportFailure(cause)
  }
}

class Service(using ec: ExecutionContext) {
  def printCorrelation(): Future[Unit] = Future {
    println(s"[${Thread.currentThread().getName}] Correlation ID: ${CorrelationContext.get()}")
  }
}

@main
def runCorrelationContextExample(): Unit = {
  val threadPool = Executors.newFixedThreadPool(2)
  val baseEc = ExecutionContext.fromExecutor(threadPool)
  val correlationId = java.util.UUID.randomUUID().toString

  given ec: ExecutionContext = CorrelationAwareExecutionContext(baseEc, correlationId)
  val service = new Service()
  val result = service.printCorrelation()

  result.onComplete {
    case Success(_) => println("Task completed successfully.")
    case Failure(exception) => println(s"Task failed with exception: ${exception.getMessage}")
  }

  Thread.sleep(1000) // Wait for the task to complete
  threadPool.shutdown()
}
import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Success, Failure}

@main
def runChainedFutures(): Unit =
  given ec: ExecutionContext = ExecutionContext.global

  def fetchUser(id: Int): Future[String] = Future {
    Thread.sleep(500)
    s"User-$id"
  }

  def fetchPosts(user: String): Future[List[String]] = Future {
    Thread.sleep(500)
    List(s"$user-Post1", s"$user-Post2")
  }

//  each step is scheduled on the execution context
  val result = for
    user <- fetchUser(1)
    posts <- fetchPosts(user)
  yield posts

  result.onComplete {
    case Success(posts) => println(s"Fetched posts: $posts")
    case Failure(ex)    => println(s"Error: ${ex.getMessage}")
  }

  Thread.sleep(2000)

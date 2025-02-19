import scala.collection.mutable

// F(n) = F(n-1) + F(n-2).
def fib(n: Int, memo: mutable.Map[Int, Int] = mutable.Map()): Int = {
  if (n <= 1) return n
  memo.getOrElseUpdate(n, fib(n - 1, memo) + fib(n - 2, memo))
}


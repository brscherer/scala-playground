def lcs(X: String, Y: String): Int = {
  val m = X.length
  val n = Y.length

  var prev = Array.fill(n + 1)(0)
  val curr = Array.fill(n + 1)(0)

  for (i <- 1 to m) {
    for (j <- 1 to n) {
      if (X(i - 1) == Y(j - 1))
        curr(j) = 1 + prev(j - 1)
      else
        curr(j) = Math.max(prev(j), curr(j - 1))
    }
    prev = curr.clone()
  }

  prev(n)
}

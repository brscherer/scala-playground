def lcs(X: String, Y: String): Int = {
  val m = X.length
  val n = Y.length
  val dp = Array.ofDim[Int](m + 1, n + 1)

  for (i <- 1 to m) {
    for (j <- 1 to n) {
      if (X(i - 1) == Y(j - 1))
        dp(i)(j) = 1 + dp(i - 1)(j - 1)
      else
        dp(i)(j) = Math.max(dp(i - 1)(j), dp(i)(j - 1))
    }
  }

  dp(m)(n)
}

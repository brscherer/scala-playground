def lps(s: String): Int = {
  val n = s.length
  if (n == 0) return 0
  val dp = Array.ofDim[Int](n, n)

  for (i <- 0 until n) {
    dp(i)(i) = 1
  }

  for (cl <- 2 to n) {
    for (i <- 0 to n - cl) {
      val j = i + cl - 1

      if (s(i) == s(j)) {
        if (cl == 2)
          dp(i)(j) = 2
        else
          dp(i)(j) = dp(i + 1)(j - 1) + 2
      } else {
        dp(i)(j) = math.max(dp(i + 1)(j), dp(i)(j - 1))
      }
    }
  }

  dp(0)(n - 1)
}

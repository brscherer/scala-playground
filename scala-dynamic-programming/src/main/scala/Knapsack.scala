def knapsack(weights: Array[Int], values: Array[Int], capacity: Int): Int = {
  val n = weights.length
  val dp = Array.ofDim[Int](n + 1, capacity + 1)

  for (i <- 1 to n) {
    for (w <- 0 to capacity) {
      if (weights(i - 1) <= w) {
        dp(i)(w) = Math.max(dp(i - 1)(w), values(i - 1) + dp(i - 1)(w - weights(i - 1)))
      } else {
        dp(i)(w) = dp(i - 1)(w)
      }
    }
  }

  dp(n)(capacity)
}

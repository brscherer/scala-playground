def unboundedKnapsack(weights: Array[Int], values: Array[Int], capacity: Int): Int = {
  val dp = Array.fill(capacity + 1)(0)

  for (w <- 0 to capacity) {
    for (i <- weights.indices) {
      if (weights(i) <= w) {
        dp(w) = Math.max(dp(w), values(i) + dp(w - weights(i)))
      }
    }
  }

  dp(capacity)
}

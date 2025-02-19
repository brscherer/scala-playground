def knapsack(weights: Array[Int], values: Array[Int], capacity: Int): Int = {
  val n = weights.length
  val dp = Array.fill(capacity + 1)(0)

  for (i <- 0 until n) {
    for (w <- capacity to weights(i) by -1) {      
      dp(w) = Math.max(dp(w), values(i) + dp(w - weights(i)))
    }
  }

  dp(capacity)
}

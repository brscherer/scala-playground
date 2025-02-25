def lis(nums: Array[Int]): Int = {
  if (nums.isEmpty) return 0

  val n = nums.length
  val dp = Array.fill(n)(1)

  for (i <- 1 until n) {
    for (j <- 0 until i) {
      if (nums(j) < nums(i)) {
        dp(i) = math.max(dp(i), dp(j) + 1)
      }
    }
  }

  dp.max
}

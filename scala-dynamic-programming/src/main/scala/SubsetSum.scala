def subsetSum(nums: Array[Int], target: Int): Boolean = {
  val n = nums.length
  val dp = Array.ofDim[Boolean](n + 1, target + 1)
  
  for (i <- 0 to n) {
    dp(i)(0) = true
  }
  
  for (j <- 1 to target) {
    dp(0)(j) = false
  }
  
  for (i <- 1 to n) {
    for (j <- 1 to target) {
      if (nums(i - 1) <= j) {
        dp(i)(j) = dp(i - 1)(j) || dp(i - 1)(j - nums(i - 1))
      } else {
        dp(i)(j) = dp(i - 1)(j)
      }
    }
  }
  
  dp(n)(target)
}

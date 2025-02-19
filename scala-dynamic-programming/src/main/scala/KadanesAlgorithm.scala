def maxSubArray(nums: Array[Int]): Int = {
  var maxEndingHere = nums(0)
  var maxSoFar = nums(0)

  for (i <- 1 until nums.length) {
    // If nums(i) is greater than maxEndingHere + nums(i), start a new subarray.
    // Otherwise, extend the current subarray.
    maxEndingHere = Math.max(nums(i), maxEndingHere + nums(i))
    maxSoFar = Math.max(maxSoFar, maxEndingHere)
  }
  
  maxSoFar
}
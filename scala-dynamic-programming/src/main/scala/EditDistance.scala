def editDistance(s1: String, s2: String): Int = {
  val n = s1.length
  val m = s2.length
  val dp = Array.ofDim[Int](n + 1, m + 1)

  // if s1 is empty, we need j insertions to form s2[0...j-1].
  for (j <- 0 to m) {
    dp(0)(j) = j
  }
  
  // if s2 is empty, we need i deletions to transform s1[0...i-1] to an empty string.
  for (i <- 0 to n) {
    dp(i)(0) = i
  }
  
  for (i <- 1 to n) {
    for (j <- 1 to m) {
      if (s1(i - 1) == s2(j - 1))
        // no additional operation is needed.
        dp(i)(j) = dp(i - 1)(j - 1)
      else {
        // Calculate the minimum edit distance by considering:
        // 1. Deletion: dp(i-1)(j)
        // 2. Insertion: dp(i)(j-1)
        // 3. Substitution: dp(i-1)(j-1)
        dp(i)(j) = 1 + math.min(dp(i - 1)(j), math.min(dp(i)(j - 1), dp(i - 1)(j - 1)))
      }
    }
  }

  dp(n)(m)
}

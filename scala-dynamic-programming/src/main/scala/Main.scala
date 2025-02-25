@main
def main(): Unit =
  println(fib(10)) // Output: 55
  println(maxSubArray(Array(-2, 1, -3, 4, -1, 2, 1, -5, 4))) // Output: 6
  println(knapsack(Array(2, 3, 4, 5), Array(3, 4, 5, 6), 5)) // Output: 7
  println(unboundedKnapsack(Array(2, 3, 4), Array(4, 5, 6), 5)) // Output: 10
  println(lcs("abcde", "ace")) // Output: 3
  println(lis(Array(10, 9, 2, 5, 3, 7, 101, 18))) // Expected Output: 4
  println(lps("bbbab")) // Expected Output: 4

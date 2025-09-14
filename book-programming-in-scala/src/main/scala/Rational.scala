class Rational(n: Int, d: Int) {
  require(d != 0, "Denominator must be non-zero")
  
  private val g = gcd(n.abs, d.abs)
  val numer: Int = n / g
  val denom: Int = d / g
  
  def this(n: Int) = this(n, 1) // auxiliary constructor
  
  def +(that: Rational): Rational = Rational(
    numer * that.denom + that.numer * denom,
    denom * that.denom
  )
  
  def +(i: Int): Rational = Rational(numer + i * denom, denom)

  def -(that: Rational): Rational = Rational(
    numer * that.denom - that.numer * denom,
    denom * that.denom
  )

  def -(i: Int): Rational = Rational(numer - i * denom, denom)

  def *(that: Rational): Rational = Rational(
    numer * that.denom ,
    denom * that.denom
  )

  def *(i: Int): Rational = Rational(numer * i, denom)

  def /(that: Rational): Rational = Rational(
    numer * that.denom,
    denom * that.numer
  )

  def /(i: Int): Rational = Rational(numer, denom * i)

  override def toString = s"$numer/$denom"
  
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
}

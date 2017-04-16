abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor:Nat = new Succ(this)
  def + (that: Nat): Nat
  def - (that: Nat): Nat

  def toInteger:Int = {
    def _toInteger(n:Nat,acc:Int):Int =
      if(n.isZero) acc
      else _toInteger(n.predecessor,acc+1)

    _toInteger(this,0)
  }
}

object Zero extends Nat {
  def isZero:Boolean = true
  def predecessor: Nat = throw new Error("no negatives")
  def + (that: Nat): Nat = that
  def - (that: Nat): Nat = if(that.isZero) this else throw new Error("no negatives")
}

class Succ(n: Nat) extends Nat {
  def isZero:Boolean = false
  def predecessor: Nat = n
  def + (that: Nat): Nat = new Succ(that + n)
  def - (that: Nat): Nat = if(that.isZero) this else n - that.predecessor
}

val one = new Succ(Zero)
val two = new Succ(one)
val three = one + two

three.toInteger
two.toInteger
(three-two).toInteger





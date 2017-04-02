package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int =
      if(c==0 || c == r) 1 else pascal(c-1,r-1)+pascal(c,r-1)
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def _balance(chars: List[Char], open: Int): Boolean =
        if (chars.isEmpty) open==0
        else
          if(chars.head == '(') _balance(chars.tail,open+1)
          else
            if(chars.head == ')') open > 0 && _balance(chars.tail,open-1)
            else _balance(chars.tail,open)

      _balance(chars,0)
    }

  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      /*def count( n, m ):
      if n < 0 or m <= 0: #m < 0 for zero indexed programming languages
      return 0
      if n == 0: # needs be checked after n & m, as if n = 0 and m < 0 then it would return 1, which should not be the case.
      return 1

      return count( n, m - 1 ) + count( n - S[m], m )*/

      def _count(n:Int, m:Int):Int = {
        if(n<0) 0
        else if(m<0) 0
        else if(n==0) 1
        else _count(n,m-1) + _count(n-coins(m),m)
      }
      _count(money,coins.length-1)
    }
  }

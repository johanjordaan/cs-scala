package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only the common elements") {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1), "Intersection 1")
      assert(!contains(s, 2), "Intersection 2")

      val su = union(s1, s2)
      val si = intersect(su,s1)
      assert(contains(si, 1), "Intersection 3")
    }
  }

  test("diff does not contain the elements in the subtracted set ") {
    new TestSets {
      val s = diff(union(s1, union(s2,s3)),s2)
      assert(contains(s, 1), "Diff 1")
      assert(contains(s, 3), "Diff 2")
      assert(!contains(s, 2), "Diff 3")
    }
  }

  test("filter returns only sets that conforms to predicate") {
    new TestSets {
      val s = filter(union(s1, union(s2,s3)),union(s1,s3))
      assert(contains(s, 1), "Filter 1")
      assert(contains(s, 3), "Filter 2")
      assert(!contains(s, 2), "Filter 3")

      val full = union(s1, union(s2,s3));
      val fs = filter(full, (e) => e>1)

      assert(!contains(fs,1), "Filter 4")
      assert(contains(fs,2), "Filter 5")
    }
  }


  test("forall") {
    new TestSets {
      val s = union(s1,s2);

      assert(forall(s, i=>i>=1), "Forall 1")
      assert(!forall(s, i=>i>3), "Forall 2")

      val ss1 = singletonSet(1)
      val ss3 = singletonSet(3)
      val ss4 = singletonSet(4)
      val ss5 = singletonSet(5)
      val ss7 = singletonSet(7)
      val ss1000 = singletonSet(1000)


      val ts = union(ss1,union(ss3,union(ss4,union(ss5,union(ss5,union(ss7,ss1000))))))

      assert(!forall(ts, i=>i<=5), "Forall 3")

    }
  }

  test("exists") {
    new TestSets {
      val s = union(s1,s2);


      val z = filter(s,i=>i==3)
      printSet(z)

      assert(exists(s, i=>i==1), "Exists 1")
      assert(!exists(s, i=>i==3), "Exists 2")
    }
  }

  test("map") {
    new TestSets {
      val s = union(s1,s2);

      assert(contains(s, 1), "Map 1")
      assert(contains(s, 2), "Map 2")

      val ms = map(s,i=>i*3)

      assert(contains(ms, 3), "Map 1")
      assert(contains(ms, 6), "Map 2")

    }
  }


}

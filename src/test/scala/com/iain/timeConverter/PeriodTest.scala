package com.iain.timeConverter

import org.scalatest.FunSuite

class periodTest extends FunSuite {

  test("Validate Short Period should return a Right(Period) for valid short form") {
    def test(p: String, exp: Duration => Period): Unit = {
      val actual = Period.shortFormToPeriod(p)
      assert(actual === Right(exp))
    }
    test("[PM]", preMatch)
    test("[H1]", firstHalf)
    test("[H2]", secondHalf)
    test("[HT]", halfTime)
    test("[FT]", fullTime)
  }

  test("Validate Short Period should return a Left(reason) for invalid short form") {
    def test(p: String) = {
      val actual = Period.shortFormToPeriod(p)
      val invalidString = s"$p is an invalid short form period"
      assert(actual === Left(invalidString))
    }

    test("PM")
    test("hello")
    test("")
    test("$%^&&*^%@")
    test("[H1")
    test("FOO")
  }


  def testToString(p: Period, exp: String): Unit = {
    val actual = Period.periodToString(p)
    assert(actual == exp)
  }

  test("Period to string for preMatch") {
    testToString(preMatch(Duration(0,0)), s"00:00 - PRE_MATCH")
    testToString(preMatch(Duration(0,1)), s"00:00 +00:01 - PRE_MATCH")
    testToString(preMatch(Duration(10,1)), s"00:00 +10:01 - PRE_MATCH")
  }

  test("Period to string for firstHalf") {
    testToString(firstHalf(Duration(0,0)), s"00:00 - FIRST_HALF")
    testToString(firstHalf(Duration(40,1)), s"40:01 - FIRST_HALF")
    testToString(firstHalf(Duration(45,0)), s"45:00 - FIRST_HALF")
    testToString(firstHalf(Duration(45,1)), s"45:00 +00:01 - FIRST_HALF")
    testToString(firstHalf(Duration(47,1)), s"45:00 +02:01 - FIRST_HALF")
    testToString(firstHalf(Duration(50,0)), s"45:00 +05:00 - FIRST_HALF")
  }

  test("Period to string for half time") {
    testToString(halfTime(Duration(0,0)), s"00:00 - HALF_TIME")
    testToString(halfTime(Duration(40,1)), s"40:01 - HALF_TIME")
    testToString(halfTime(Duration(45,0)), s"45:00 - HALF_TIME")
    testToString(halfTime(Duration(45,1)), s"45:00 +00:01 - HALF_TIME")
    testToString(halfTime(Duration(47,1)), s"45:00 +02:01 - HALF_TIME")
    testToString(halfTime(Duration(50,0)), s"45:00 +05:00 - HALF_TIME")
  }

  test("Period to string for second half") {
    testToString(secondHalf(Duration(0,0)), s"00:00 - SECOND_HALF")
    testToString(secondHalf(Duration(45,0)), s"45:00 - SECOND_HALF")
    testToString(secondHalf(Duration(45,1)), s"45:01 - SECOND_HALF")
    testToString(secondHalf(Duration(70,50)), s"70:50 - SECOND_HALF")
    testToString(secondHalf(Duration(90,0)), s"90:00 - SECOND_HALF")
    testToString(secondHalf(Duration(90,1)), s"90:00 +00:01 - SECOND_HALF")
    testToString(secondHalf(Duration(95,1)), s"90:00 +05:01 - SECOND_HALF")
  }

  test("Period to string for full time") {
    testToString(fullTime(Duration(90,0)), s"90:00 +00:00 - FULL_TIME")
    testToString(fullTime(Duration(90,1)), s"90:00 +00:01 - FULL_TIME")
    testToString(fullTime(Duration(95,1)), s"90:00 +05:01 - FULL_TIME")
  }
}

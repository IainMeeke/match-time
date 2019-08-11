package com.iain.timeConverter

import org.scalatest.FunSuite

class TimeConverterTest extends FunSuite {

  test("testConvert") {
    assert(5 === TimeConverter.convert("hello"))
  }

}

class periodTest extends FunSuite {

  test("Validate Short Period should return a Right(Period) for valid short form") {
    def test(p: String, exp: String => Period): Unit = {
      val actual = TimeConverter.validateShortPeriod(p)
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
      val actual = TimeConverter.validateShortPeriod(p)
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
}

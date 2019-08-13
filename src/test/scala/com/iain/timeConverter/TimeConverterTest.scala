package com.iain.timeConverter

import org.scalatest.FunSuite

class TimeConverterTest extends FunSuite {

  test("Convert returns the period as a string for a valid input") {
    def test(input: String, exp: String) = {
      val actual = TimeConverter.convert(input)
      assert(actual == exp)
    }

    test("[PM] 0:00.000", "00:00 - PRE_MATCH")
    test("[PM] 0:00.000", "00:00 - PRE_MATCH")
    test("[H1] 0:15.025", "00:15 - FIRST_HALF")
    test("[H1] 3:07.513", "03:08 - FIRST_HALF")
//    test("[H1] 45:00.001", "45:00 +00:00 - FIRST_HALF")
    test("[H1] 46:15.752", "45:00 +01:16 - FIRST_HALF")
    test("[HT] 45:00.000", "45:00 - HALF_TIME")
    test("[H2] 45:00.500", "45:01 - SECOND_HALF")
    test("[H2] 90:00.908", "90:00 +00:01 - SECOND_HALF")
    test("[FT] 90:00.000", "90:00 +00:00 - FULL_TIME")
    test("90:00", "INVALID")
    test("[H3] 90:00.000", "INVALID")
    test("[PM] -10:00.000", "INVALID")
    test("FOO", "INVALID")
  }

  test("validate input returns a right(period) for valid input") {
    val expected = Right(fullTime(Duration(90,0)))
    val actual = TimeConverter.validateInput(s"[FT] 90:00.000")
    assert(actual === expected)
  }

  test("validate input returns a left for invalid input") {
    assert(TimeConverter.validateInput(s"90:00").isLeft)
    assert(TimeConverter.validateInput(s"[H3] 90:00.000").isLeft)
    assert(TimeConverter.validateInput(s"[PM] -10:00.000").isLeft)
    assert(TimeConverter.validateInput(s"FOO").isLeft)
  }

}

package com.iain.timeConverter

import org.scalatest.FunSuite

class DurationTest extends FunSuite {

  test("Parse duration no milliseconds should parse without rounding") {
    assert(Duration.parseDuration("1:1.0") === Right(Duration(1,1)))
    assert(Duration.parseDuration("60:30.0") === Right(Duration(60,30)))
    assert(Duration.parseDuration("90:50.0") === Right(Duration(90,50)))
  }

  test("Parse duration milliseconds < 500 should parse with rounding down") {
    assert(Duration.parseDuration("1:1.400") === Right(Duration(1,1)))
    assert(Duration.parseDuration("60:30.499") === Right(Duration(60,30)))
    assert(Duration.parseDuration("90:50.3") === Right(Duration(90,50)))
  }

  test("Parse duration milliseconds > 500 should parse with rounding up") {
    assert(Duration.parseDuration("1:1.600") === Right(Duration(1,2)))
    assert(Duration.parseDuration("60:30.500") === Right(Duration(60,31)))
    assert(Duration.parseDuration("90:50.999") === Right(Duration(90,51)))
  }

  test("Parse duration milliseconds > 500 and seconds = 59 should parse with rounding minute up") {
    assert(Duration.parseDuration("1:59.600") === Right(Duration(2,0)))
    assert(Duration.parseDuration("60:59.500") === Right(Duration(61,0)))
    assert(Duration.parseDuration("90:59.999") === Right(Duration(91, 0)))
  }

  test("Parse duration milliseconds > 599 should fail") {
    assert(Duration.parseDuration("1:59.6000").isLeft)
    assert(Duration.parseDuration("60:59.5000").isLeft)
    assert(Duration.parseDuration("90:59.1000").isLeft)
  }

  test("Parse duration seconds > 599 should fail") {
    assert(Duration.parseDuration("1:60.0").isLeft)
    assert(Duration.parseDuration("60:60.0").isLeft)
    assert(Duration.parseDuration("90:60.0").isLeft)
  }

  test("Parse duration incorrect format should fail") {
    assert(Duration.parseDuration("1.60:0").isLeft)
    assert(Duration.parseDuration("1:60:60").isLeft)
    assert(Duration.parseDuration("1.60.60").isLeft)
    assert(Duration.parseDuration("1:60,60").isLeft)
    assert(Duration.parseDuration("hello").isLeft)
  }

  test("To string pads correctly"){
    assert(Duration(1,1).toString() == s"01:01")
    assert(Duration(90,0).toString() == s"90:00")
    assert(Duration(9,5).toString() == s"09:05")
    assert(Duration(10,1).toString() == s"10:01")
    assert(Duration(0,0).toString() == s"00:00")
  }

  test("> should return true if a duration is greater than another") {
    assert(Duration(10,0) > Duration(5, 50))
    assert(Duration(10,50) > Duration(10, 0))
  }

  test("> should return false if a duration is less than another") {
    assert((Duration(10,50) > Duration(20, 0)) == false)
    assert((Duration(20,50) > Duration(20, 60)) == false)
  }
}



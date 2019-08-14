package com.iain.timeConverter

import org.scalatest.FunSuite

class DurationTest extends FunSuite {

  test("Parse duration should parse successfully") {
    assert(Duration.parseDuration("1:1.0") === Right(Duration(1,1,0)))
    assert(Duration.parseDuration("60:30.100") === Right(Duration(60,30,100)))
    assert(Duration.parseDuration("90:50.600") === Right(Duration(90,50,600)))
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

  test("Duration to string pads correctly"){
    assert(Duration.durationToString(Duration(1,1,0)) == s"01:01")
    assert(Duration.durationToString(Duration(90,0,0)) == s"90:00")
    assert(Duration.durationToString(Duration(9,5,0)) == s"09:05")
    assert(Duration.durationToString(Duration(10,1,0)) == s"10:01")
    assert(Duration.durationToString(Duration(0,0,0)) == s"00:00")
  }

  test("Duration to string rounds correctly") {
    assert(Duration.durationToString(Duration(1, 1, 600)) == s"01:02")
    assert(Duration.durationToString(Duration(1, 59, 600)) == s"02:00")
    assert(Duration.durationToString(Duration(1, 1, 0)) == s"01:01")
  }

  test("> should return true if a duration is greater than another") {
    assert(Duration(10,0,0) > Duration(5, 50,0))
    assert(Duration(10,50,0) > Duration(10, 0,0))
    assert(Duration(10,50,5) > Duration(10, 50,0))
  }

  test("> should return false if a duration is less than another") {
    assert((Duration(10,50,0) > Duration(20, 0,0)) == false)
    assert((Duration(20,50,0) > Duration(20, 60,0)) == false)
    assert((Duration(20,50,0) > Duration(20, 50,1)) == false)
  }

  test("Round duration milliseconds < 500 should round down") {
    assert(Duration.roundDuration(Duration(1, 1, 400)) === Duration(1,1,0))
    assert(Duration.roundDuration(Duration(60, 30, 499)) === Duration(60,30,0))
    assert(Duration.roundDuration(Duration(90, 50, 3)) === Duration(90,50,0))
  }

  test("Round duration milliseconds > 500 should round up") {
    assert(Duration.roundDuration(Duration(1, 1, 600)) === Duration(1,2,0))
    assert(Duration.roundDuration(Duration(60, 30, 500)) === Duration(60,31,0))
    assert(Duration.roundDuration(Duration(90, 50, 999)) === Duration(90,51,0))
  }

  test("Parse duration milliseconds > 500 and seconds = 59 should parse with rounding minute up") {
    assert(Duration.roundDuration(Duration(1, 59, 600)) === Duration(2,0,0))
    assert(Duration.roundDuration(Duration(60, 59, 500)) === Duration(61,0,0))
    assert(Duration.roundDuration(Duration(90, 59, 999)) === Duration(91, 0,0))
  }
}



package com.iain.timeConverter

import org.scalatest.FunSuite

class TimeConverterTest extends FunSuite {

  test("testConvert") {
    assert(5 === TimeConverter.convert("hello"))
  }

}

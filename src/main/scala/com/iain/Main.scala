package com.iain

import com.iain.timeConverter.TimeConverter

object Main extends App {
  require(args.length == 1, "Usage: sbt <input>")
  val input = args(0)
  TimeConverter.convert(input)
}

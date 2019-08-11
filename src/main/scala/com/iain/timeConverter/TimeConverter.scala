package com.iain.timeConverter

import com.typesafe.scalalogging.LazyLogging

object TimeConverter extends LazyLogging {

  def convert(input: String) = {
    logger.debug(s"here is the string from lazy log: ${input}")
    5
  }

  /**
    * The input match time is in the format
    * [period] minutes:seconds.milliseconds
    *
    * @param input
    * @return
    */
  def validate(input: String): Boolean = {
    input.split(" ")
  }
}

trait Period {
  val shortForm: String
  val longForm: String
}
case class preMatch(time: String) extends Period {
  override val shortForm: String = "PM"
  override val longForm: String = "PRE_MATCH"
}
case class firstHalf(time: String) extends Period {
  override val shortForm: String = "H1"
  override val longForm: String = "FIRST_HALF"
}

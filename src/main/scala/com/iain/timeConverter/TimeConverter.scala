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
    val separate = input.split(" ")
    val periodValidated: Either[String, String => Period] = separate.size match {
      case invalidLength if invalidLength !=2 => Left("invalid")
      case valid => validateShortPeriod(separate(0))
    }

//    val validateTime
    periodValidated.map(per => per("hello"))
  true
  }


  /**
    * Validates that the provided short form period is the correct format. The provided
    * period should be wrapped in square braces as `[period]`.
    *
    * If the period is valid then a Right of the constructor for that period is returned.
    * If the period is invalid then a Left of a string is returned.
    * @param period a short form period wrapped in square braces
    * @return Either a left of error or a period constructor
    */
  def validateShortPeriod(period: String): Either[String, String => Period] = {
    period match {
      case "[PM]" => Right(preMatch)
      case "[H1]" => Right(firstHalf)
      case "[HT]" => Right(halfTime)
      case "[H2]" => Right(secondHalf)
      case "[FT]" => Right(fullTime)
      case invalid => Left(s"$invalid is an invalid short form period")
    }
  }
}

sealed trait Period {
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
case class halfTime(time: String) extends Period {
  override val shortForm: String = "HT"
  override val longForm: String = "HALF_TIME"
}
case class secondHalf(time: String) extends Period {
  override val shortForm: String = "H2"
  override val longForm: String = "SECOND_HALF"
}
case class fullTime(time: String) extends Period {
  override val shortForm: String = "FT"
  override val longForm: String = "FULL_TIME"
}


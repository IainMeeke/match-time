package com.iain.timeConverter

import com.typesafe.scalalogging.LazyLogging

object TimeConverter extends LazyLogging {

  def convert(input: String) = validateInput(input) match {
    case Right(validFormat) => Period.periodToString(validFormat)
    case Left(err) => {
      logger.error(s"Input was invalid: $err")
      "INVALID"
    }
  }

  /**
    * Validate that the input match time is in the format [period] minutes:seconds.milliseconds.
    * If it is constrcut a Period(Durtion()) from it.
    * If not return a Left(failure message)
    *
    * @param input
    * @return
    */
  def validateInput(input: String): Either[String, Period] = {
    val separate = input.split(" ")

    //validate the period and duration component and construct a Period if both are valid
    separate.size match {
      case invalidLength if invalidLength != 2 => Left("INVALID")
      case valid => Period.shortFormToPeriod(separate(0)).map(per => {
        Duration.parseDuration(separate(1)).map(per(_))
      }).getOrElse(Left("INVALID"))
    }
  }






}




package com.iain.timeConverter


sealed abstract class Period {
  val upperBound: Duration
  val time: Duration
}

case class preMatch(time: Duration) extends Period {
  val upperBound = Duration(0, 0, 0)
}

case class firstHalf(time: Duration) extends Period {
  val upperBound = Duration(45, 0, 0)
}

case class halfTime(time: Duration) extends Period {
  val upperBound = Duration(45, 0, 0)
}

case class secondHalf(time: Duration) extends Period {
  val upperBound = Duration(90, 0, 0)
}

case class fullTime(time: Duration) extends Period {
  val upperBound = Duration(90, 0, 0)
}

/**
  * Period companion object
  */
object Period {

  /**
    * Validates that the provided short form period is the correct format. The provided
    * period should be wrapped in square braces as `[period]`.
    *
    * If the period is valid then a Right of the constructor for that period is returned.
    * If the period is invalid then a Left of a string is returned.
    *
    * @param period a short form period wrapped in square braces
    * @return Either a left of error or a period constructor
    */
  def shortFormToPeriod(period: String): Either[String, Duration => Period] = {
    period match {
      case "[PM]" => Right(preMatch)
      case "[H1]" => Right(firstHalf)
      case "[HT]" => Right(halfTime)
      case "[H2]" => Right(secondHalf)
      case "[FT]" => Right(fullTime)
      case invalid => Left(s"$invalid is an invalid short form period")
    }
  }

  /**
    * Converts a period to it's long form string
    * @param period
    * @return
    */
  def periodToLongForm(period: Period): String = period match {
    case _: preMatch => "PRE_MATCH"
    case _: firstHalf => "FIRST_HALF"
    case _: halfTime => "HALF_TIME"
    case _: secondHalf => "SECOND_HALF"
    case _: fullTime => "FULL_TIME"
  }

  /**
    * Converts a period and its time component to a string. If the time component is greater than the
    * period's upperbound then an overtime component is added to the string.
    *
    * Note that the fullTime period always has the overtime component.
    *
    * @param per
    * @return
    */
  def periodToString(period: Period): String =  {

    def periodToStringWithOvertime(per: Period): String = {
      val roundedDur = Duration.roundDuration(per.time)
      val overTime = Duration(roundedDur.minutes - per.upperBound.minutes, roundedDur.seconds - per.upperBound.seconds, 0)
      s"${Duration.durationToString(per.upperBound)} +${Duration.durationToString(overTime)} - ${periodToLongForm(per)}"
    }

    period match {
      case per: fullTime  => periodToStringWithOvertime(per)  //full time always displays the overtime period as per the design doc
      case per: Period =>
        if (per.time > per.upperBound) periodToStringWithOvertime(per) //if there is overtime
        else s"${Duration.durationToString(per.time)} - ${periodToLongForm(per)}"
    }
  }
}

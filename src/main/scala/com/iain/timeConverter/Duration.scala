package com.iain.timeConverter

import scala.util.{Failure, Success, Try}

case class Duration(minutes: Int, seconds: Int, milliseconds: Int) {

  /**
    * define greater than to allow durations to be compared
    * @param dur
    * @return
    */
  def >(dur: Duration): Boolean = {
    if(this.minutes > dur.minutes) true
    else if(this.minutes == dur.minutes && this.seconds > dur.seconds) true
    else if(this.minutes == dur.minutes && this.seconds == dur.seconds && this.milliseconds > dur.milliseconds) true
    else false
  }
}

object Duration {
  /**
    * Parse a string in the format `minutes:seconds.milliseconds` into a Right(Duration) object.
    * The milliseconds value in the input will be rounded to the nearest whole second.
    *
    * The seconds must be less than 60 (60 falls into the next minute), similarly, milliseconds must be
    * less than 1000.
    *
    * If the input is not in the correct format then a Left(reason) will be returned
    *
    * @param input
    * @return
    */
  def parseDuration(input: String): Either[String, Duration] = {
    Try {
      val mins :: secMilli = input.split(':').toList
      val secs :: milli = secMilli.head.split('.').toList
      val millis = milli.head

      if (secs.toInt >= 60) throw new IllegalArgumentException(s"Maximum of 60 seconds per minute")
      else if (millis.toInt > 999) throw new IllegalArgumentException(s"Maximum of 999 milliseconds per second")
      else if (mins.toInt < 0 || secs.toInt < 0 || millis.toInt < 0) throw new IllegalArgumentException("values must be greater than 0")
      else Duration(mins.toInt, secs.toInt, millis.toInt)
    }
    match {
      case Success(v) => Right(v)
      case Failure(e) => Left(s"Failure parsing duration: $e")
    }
  }

  /**
    * Rounds the milliseconds of duration to the nearest second and returns a new Duration with the rounded time.
    * Rounds up to the next second if ms>=500, otherwise rounds down.
    *
    * Rounding the second up could cause it to go the next minute. In that case the minute will be incremented and seconds
    * set to 0.
    *
    * @param dur
    * @return
    */
  def roundDuration(dur: Duration): Duration = {
    val secsRounded: Int = if (dur.milliseconds >= 500) dur.seconds + 1 else dur.seconds

    //Rounding the second could result in an extra minute
    if (secsRounded >= 60) Duration(dur.minutes + 1, 0, 0)
    else Duration(dur.minutes, secsRounded, 0)
  }

  /**
    * Rounds a duration using the roundDuration function, then returns the minutes and second as a string.
    * The padding will ensure that the minutes and seconds are both 2 digits.
    *
    * @param dur
    * @return
    */
  def durationToString(dur: Duration): String = {
    def pad(v: Int): String = if (v < 10) s"0$v" else s"$v"
    val rounded = roundDuration(dur)
    s"${pad(rounded.minutes)}:${pad(rounded.seconds)}"
  }
}


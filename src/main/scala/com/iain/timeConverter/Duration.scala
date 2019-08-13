package com.iain.timeConverter

import scala.util.{Failure, Success, Try}

case class Duration(minutes: Int, seconds: Int) {
  override def toString(): String = {
    def pad(v: Int): String = if (v < 10) s"0$v" else s"$v"
    s"${pad(minutes)}:${pad(seconds)}"
  }

  /**
    * define greater than to allow durations to be compared
    * @param dur
    * @return
    */
  def >(dur: Duration): Boolean = {
    if(this.minutes > dur.minutes) true
    else if(this.minutes == dur.minutes && this.seconds > dur.seconds) true
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
      else if (mins.toInt < 0 || secs.toInt < 0 || millis.toInt < 0)
        throw new IllegalArgumentException("values must be greater than 0")
      else {
        val secsRounded = if (millis.toInt >= 500) secs.toInt + 1 else secs.toInt

        //Rounding the second could result in an extra minute
        if (secsRounded >= 60) Duration(mins.toInt + 1, 0)
        else Duration(mins.toInt, secsRounded.toInt)
      }
    }
    match {
      case Success(v) => Right(v)
      case Failure(e) => Left(s"Failure parsing duration: $e")
    }
  }

}


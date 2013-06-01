package com.eddandkrista.week

import util.Try
import org.joda.time.format.DateTimeFormat

trait Remind {

  class RemindProvider(src: String) {
    new RemindCalendar(src)
  }

  // maybe calendar should just be a container
  case class RemindCalendar(calendarSrc: String) extends Calendar {
    def getWeekEvents = {
      Rem getThisWeek(calendarSrc)
    }
  }

  object Rem extends RemindParser {
    import sys.process._

    def getThisWeek(calendarSrc: String): WeekList = {
      val remind = Seq("remind", "-s+1", calendarSrc)
      fromEventStream(remind.lines flatMap parse)
    }
  }
}

trait RemindParser extends EventParser {
  /**
   * Takes a remind -s output string,
   * and returns an event if possible.
   * We're pretty in the try, because we can be -- any false assumptions means
   * the line can't be parsed and should return None.
   */
  def parse(input: String) = Try {
    val parts = input.split(" ")

    val day = DateTimeFormat.forPattern("yyyy/MM/dd").parseMillis(parts(0))
    val start =
      if (parts(4) == "*") None
      else Some(day + (parts(4).toLong * 60 * 1000))
    val duration =
      if (parts(3) == "*") None
      else Some(parts(3).toInt * 60 * 1000)

    // remind puts the times in description if they exist.
    val prefix = if (parts(4) == "*") 5 else 6
    val description = parts drop prefix mkString " "

    (day, event(description, start, duration))
  } toOption
}

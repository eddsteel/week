package com.eddandkrista.week

import util.Try
import org.joda.time.format.DateTimeFormat

trait Remind {

  // TODO: pull in config and do this.
  //class RemindProvider(config: Config)

  class RCalendar(calendarSrc: String) extends Calendar {
    def getWeekEvents = {
      Rem getThisWeek(calendarSrc) sortBy (_._1)
    }
  }

  object Rem extends RemindParser {
    import sys.process._

    def getThisWeek(calendarSrc: String): Vector[(Long, Stream[Event])] = {
      // TODO: remind with configured source
      val remind = Seq("rem", "-s+1")
      val parsed = (remind.lines map parse flatten)
      val grouped = parsed groupBy (_._1)
      val flattened = grouped mapValues (stream => stream map (_._2)) 
      flattened toVector
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

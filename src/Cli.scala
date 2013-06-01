package com.eddandkrista.week

/**
 * TODO: fade past
 * TODO: highlight present
 */
trait Cli extends Week { this: CalendarProvider with TimeConversions =>
  private type Event = Week#Event
  private val break = "\n"
  private val indent = " "

  def main(args: Array[String]) {

    val weekLists: Seq[WeekList] = getCalendars map (_ getWeekEvents)
    if (weekLists.nonEmpty) {
      val combined: WeekList = weekLists reduce merge

      renderWeek(combined)
    }
  }

  def renderWeek(week: WeekList) =
    (week sorted) map {
      case (d, es) =>
        break + shortDay(d) + break + break +
        ((es sorted) map { e => renderEvent(e) } mkString "\n")
    } map (println _)

  def renderEvent(e: Event) = indent +
    (e.startTime map justTime map (_ + ": ") getOrElse "") +
      e.description +
      (e.duration map ( d => s" (until ${justTime(e.startTime.get + d)})") getOrElse "")
}


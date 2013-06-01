package com.eddandkrista.week

/** Model */
trait Week {
  case class Event(
    val description: String,
    val startTime: Option[Long],
    val duration: Option[Int]
  ) {
    require(if (duration.isDefined) startTime.isDefined else true)
  }

  type Day = (Long, Seq[Week#Event])
  type WeekList = Vector[Day]

  implicit object WeekOrdering extends math.Ordering[Day] {
    def compare(a: Day, b: Day) = a._1 compare b._1
  }

  implicit object DayOrdering extends math.Ordering[Week#Event] {
    def compare(a: Week#Event, b: Week#Event) = (a.startTime, b.startTime) match {
      case (None, None) => 0
      case (_, None) => 1
      case (None, _) => -1
      case _ => 0
    }
  }

  def event(
    description: String,
    startTime: Option[Long] = None,
    duration: Option[Int] = None) =
      new Event(description, startTime, duration)

  def merge(events: WeekList, moreEvents: WeekList) = (
    (events zip moreEvents) map {
      case ((d, es), (_, fs)) => (d, (es ++ fs) sorted)
    }
  ) sorted
}

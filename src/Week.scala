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

  def toEventStream(week: WeekList): Stream[(Long, Week#Event)] = week match {
    case Seq() => Stream()
    case Seq((day, events), rest @ _*) =>
      ((events toStream) map {event: Week#Event => day -> event}) #:::
        toEventStream(rest toVector)
  }

  def fromEventStream(events: Stream[(Long, Week#Event)]): WeekList = {
    val grouped = events groupBy (_._1)
    val flattened = grouped mapValues (stream => stream map (_._2))

    ((flattened toVector): WeekList) sorted
  }

  def merge(week1: WeekList, week2: WeekList) =
    fromEventStream(toEventStream(week1) union toEventStream(week2))
}

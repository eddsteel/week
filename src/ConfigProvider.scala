package com.eddandkrista.week

// TODO: make it use config.
// TODO: extract Remind out
trait ConfigProvider extends Week with CalendarProvider with Remind {

  def getCalendars = List(
    new RCalendar("")
  )
}


//TODO -> test
trait DummyProvider extends Week with CalendarProvider {
  def getCalendars = List(
    new Calendar {
      def getWeekEvents = week zip Vector(
        Nil, Nil, Nil, Nil, Nil, Nil,
        List(event("Eat food", Some(2500L)))
      )
    }
  )

  private def week = ((0 until 7) map (_ * day)) toVector

  private val day = (1000 * 60 * 60 * 24) toLong
}

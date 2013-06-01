package com.eddandkrista.week

trait CalendarProvider {
  def getCalendars: Seq[Calendar]
}

package com.eddandkrista.week

trait TimeConversions {
  implicit def longAsDate(l: Long): java.util.Date = new java.util.Date(l)

  def justTime(d: Long): String

  def shortDay(d: Long): String
}

trait StdConversions extends TimeConversions {
  private val timef = new java.text.SimpleDateFormat("HH:mm")
  private val shortf = new java.text.SimpleDateFormat("E d/M")


  def justTime(d: Long): String = timef format d

  def shortDay(d: Long): String = shortf format d
}

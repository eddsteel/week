package com.eddandkrista.week

import com.typesafe.config._

import collection.JavaConversions._
import util.Try

trait ConfigProvider extends Week with CalendarProvider with Remind {

  val fallback = ConfigFactory.load
  val home = System.getProperty("user.home")
  val userConfig = s"${home}/.config/weekrc"
  val user = Try(ConfigFactory.parseReader(new java.io.FileReader(userConfig))).toOption

  val config = user.getOrElse(ConfigFactory.empty()).withFallback(fallback).resolve
  
  def configToCalendar(config: Config) = config.getString("type") match {
    case "remind" =>
      new Some(RemindCalendar(config.getString("src")))

    case t =>
      println(s"Unknown type: $t")
      None
  }

  def getCalendars = {
    val configList = ((config getConfigList("calendars")) toList)
    if (configList isEmpty) println(s"Please define some calendars in ${userConfig}")

    (configList map configToCalendar) flatten
  }
}


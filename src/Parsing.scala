package com.eddandkrista.week

trait EventParser extends Week {
  type Event = Week#Event

  def parse(input: String): Option[(Long, Event)]
}

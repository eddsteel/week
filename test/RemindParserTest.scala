package com.eddandkrista.week

import org.joda.time._
import org.scalatest.FunSpec

class RemindParserTest extends RemindParser with FunSpec {
  describe("Remind format parsing") {

    val d28th = new DateMidnight(2013, 5, 28).getMillis
    val sixpm = new DateTime(2013, 5, 28, 18, 0).getMillis
    val fiveHours = 5 * 60 * 60 * 1000

    it ("should accept appointments") {
      assert(
        parse("2013/05/28 * * * * Bowling Day!") ===
        Some((d28th, event("Bowling Day!", None, None)))
      )
    }

    it ("should accept appointments with starts") {
      assert(
        parse("2013/05/28 * * * 1080 6:00pm It's happening") ===
        Some((d28th, event("It's happening", Some(sixpm), None)))
      )
    }

    it ("should accept appointments with starts/durations") {
      assert(
        parse("2013/05/28 * * 300 1080 6:00-11:00pm Bowling") ===
        Some((d28th, event("Bowling", Some(sixpm), Some(fiveHours))))
      )
    }
  }
}

package com.quantal.oddsscraper

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner._
//import runner._

@RunWith(classOf[JUnitRunner])
object MySpec extends SpecificationWithJUnit{
  "This wonderful system" should {
    "save the world" in {
      val list = Nil
      list must beEmpty
    }
  }
}

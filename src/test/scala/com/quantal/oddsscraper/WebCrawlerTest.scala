package com.quantal.oddsscraper

import org.junit._
import org.junit.Before
import Assert._
import com.quantal.oddsscraper.crawler._
import java.util.Calendar

//import org.specs.specification.SpecificationStructure

//import org.scalatest.junit.JUnitRunner


//import runner._
//@RunWith(classOf[JUnitRunner])
class WebCrawlerTest {

  var webCrawler: WebCrawler = null;
  var websites: List[String] = List();

  @Before
  def setupTest(): Unit = {
    webCrawler = new WebCrawler()
    //websites =  websites.::("http://en.wikipedia.org/")
    // websites =  websites.+:( "https://www.google.com")
    websites = websites.+:("http://www.oddschecker.com/football/english/premier-league")
    //return Unit
  }

  @Test
  def testCrawl = {
    webCrawler crawl websites
    assertTrue(true)

  }


  @Test
  def testLineups(){
        var homeTeam = "Arsenal"
        var awayTeam= "Everton"
        var dateFromCal = Calendar.getInstance
        dateFromCal.set(2013,Calendar.DECEMBER,8)
        var matchDate = dateFromCal.getTime
        var lineup = webCrawler.getTeamLineups(homeTeam, awayTeam, matchDate)
        assertTrue(!lineup.isEmpty)
  }
  //@Test
  //def test
}
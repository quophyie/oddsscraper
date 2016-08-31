package ccom.quantal.oddsscraper

/**
 * Created with IntelliJ IDEA.
 * User: dman
 * Date: 12/09/2013
 * Time: 00:04
 * To change this template use File | Settings | File Templates.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


//@RunWith(value=classOf[org.specs2.runner.JUnitRunner])
@RunWith(value = classOf[Suite])
@Suite.SuiteClasses(value = Array(classOf[com.quantal.oddsscraper.WebCrawlerTest],
  classOf[com.quantal.oddsscraper.AppTest],
  classOf[com.quantal.oddsscraper.connection.CassandraDatabaseConnectionTest],
  classOf[com.quantal.oddsscraper.dao.OddsScraperDAOTest])
)
class TestRunner {

}

/*import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert._
import org.junit.runners.Suite.SuiteClasses
import org.scalatest.junit.AssertionsForJUnit

class SampleTestA extends AssertionsForJUnit {
  val sb = new StringBuilder

  @Before
  def init{
    sb.append("Sample Test A")
  }

  @Test
  def testPattern1{
    sb.append(": pattern 1")
    assert("Sample Test A: pattern 1" === sb.toString)
  }
}

class SampleTestB extends AssertionsForJUnit {
  val sb = new StringBuilder

  @Before
  def init{
    sb.append("Sample Test B")
  }

  @Test
  def testPattern1{
    sb.append(": pattern 1")
    assert("Sample Test B: pattern 1" === sb.toString)
  }
}

@RunWith(value=classOf[org.junit.runners.Suite])
@SuiteClasses(value=Array(classOf[SampleTestA],classOf[SampleTestB]))
class SampleSuite {

}*/

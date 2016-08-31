package com.quantal.oddsscraper.dao

/**
 * Created with IntelliJ IDEA.
 * User: dman
 * Date: 03/10/2013
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */

import org.junit._
import com.quantal.oddsscraper.models.database.MatchDataModel
import java.util.Date
import com.quantal.oddsscraper.traits.connect.TDatabaseConnection
import com.quantal.oddsscraper.connection.CassandraDatabaseConnection
import java.util

class OddsScraperDAOTest {
  private var matchData: MatchDataModel = _
  private var oddsScraperDAO: OddsScraperDAO = _

  @Before
  def setup() {
    val dbConn: TDatabaseConnection = new CassandraDatabaseConnection("centos-db-svr.local")
    oddsScraperDAO = new OddsScraperDAO(dbConn)

    var awaySquad = new util.ArrayList[String]()
    awaySquad.add("Mackenzie");
    awaySquad.add("Holt");

    var homeSquad = new util.ArrayList[String]()
    homeSquad.add("Wiltshire");
    homeSquad.add("Ozil");

    matchData = new MatchDataModel
    matchData.OddsId = (new Date()).getTime()
    matchData.AwayOdds = "9/4"
    matchData.AwayTeamName = "Norwich"
    matchData.AwayTeamSquad = awaySquad
    matchData.DrawOdds ="7/4"
    matchData.HomeOdds = "8/9"
    matchData.HomeTeamName = "Arsenal"
    matchData.HomeTeamSquad = homeSquad;
    matchData.MatchDate = new Date
    matchData.MatchTime = new Date
    matchData.RefereeFname = "Howard"
    matchData.RefereeLname = "Webb"
    matchData.HomeTeamFinalScore = 5
    matchData.AwayTeamFinalScore = 0
    matchData.Result = "H"
    matchData.User = "quophyie"
  }

  @Test
  def testAddData() {
    oddsScraperDAO.addData(matchData)
  }
}

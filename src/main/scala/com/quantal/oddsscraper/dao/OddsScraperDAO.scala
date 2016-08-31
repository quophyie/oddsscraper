package com.quantal.oddsscraper.dao

import com.quantal.oddsscraper.traits.connect.TDatabaseConnection
import com.quantal.oddsscraper.traits.dao.TOddsScraperDAO
import com.datastax.driver.core.{BoundStatement, PreparedStatement, Session}
import com.quantal.oddsscraper.models.database.MatchDataModel

/**
 * Created with IntelliJ IDEA.
 * User: dman
 * Date: 26/09/2013
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */
class OddsScraperDAO(dbConnection: TDatabaseConnection) extends TOddsScraperDAO {

  private var session: Session = dbConnection.DBSession;

  private var strQuery: String = _
  private var prepStmt: PreparedStatement = _
  private var boundStmt: BoundStatement = _

  override def getUser(username: String) {

  }

  override def addData(saveData: MatchDataModel) {
    strQuery = "INSERT INTO ODDSSCRAPER_KEYSPACE.ODDSSCRAPER " +
      "(odds_id," +
      "away_oods," +
      "away_team," +
      "away_team_squad," +
      "draw_odds," +
      "home_odds," +
      "home_team," +
      "home_team_squad," +
      "match_date," +
      "match_time," +
      "referee_fname," +
      "referee_lname," +
      "hometeamfinalscore," +
      "awayteamfinalscore," +
      "result," +
      "user ) " +
      "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    prepStmt = session.prepare(strQuery)
    boundStmt = new BoundStatement(prepStmt)
    session.execute(boundStmt.bind(
      saveData.OddsId,
      saveData.AwayOdds,
      saveData.AwayTeamName,
      saveData.AwayTeamSquad,
      saveData.DrawOdds,
      saveData.HomeOdds,
      saveData.HomeTeamName,
      saveData.HomeTeamSquad,
      saveData.MatchDate,
      saveData.MatchTime,
      saveData.RefereeFname,
      saveData.RefereeLname,
      saveData.HomeTeamFinalScore,
      saveData.AwayTeamFinalScore,
      saveData.Result,
      saveData.User))
    //session.execute(strQuery)
  }
}

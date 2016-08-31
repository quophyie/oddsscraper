package com.quantal.oddsscraper.models.database

import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: dman
 * Date: 02/10/2013
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
class MatchDataModel {
  var OddsId: java.lang.Long = _
  var AwayOdds: String = _
  var AwayTeamName: String = _
  var AwayTeamSquad: java.util.List[String] = _
  var DrawOdds: String = _
  var HomeOdds: String = _
  var HomeTeamName: String = _
  var HomeTeamSquad: java.util.List[String] = _
  var MatchDate: Date = _
  var MatchTime: Date = _
  var RefereeFname: String = _
  var RefereeLname: String = _
  var HomeTeamFinalScore: Integer = _
  var AwayTeamFinalScore: Integer = _
  var Result: String = _
  var User: String = _
}

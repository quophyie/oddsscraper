package com.quantal.oddsscraper.traits.dao

import com.quantal.oddsscraper.models.database.MatchDataModel

/**
 * Created with IntelliJ IDEA.
 * User: dman
 * Date: 26/09/2013
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */
trait TOddsScraperDAO {

  def getUser(username: String): Unit

  def addData(saveData: MatchDataModel): Unit
}

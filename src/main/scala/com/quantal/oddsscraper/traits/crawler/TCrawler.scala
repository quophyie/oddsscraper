package com.quantal.oddsscraper.traits.crawler

import org.jsoup.nodes.Document
import com.quantal.oddsscraper.models.database.MatchDataModel
import com.quantal.oddsscraper.models.domain.Player
import java.util.Date

trait TCrawler {

  def crawl(listOfList: List[String]): List[Document]

  def processDocs(listOfDocs: List[Document])

  def extractData(doc: Document): List[MatchDataModel]

  def getTeamLineups(homeTeam:String, awayTeam:String, matchDate:Date):List[Player];
}
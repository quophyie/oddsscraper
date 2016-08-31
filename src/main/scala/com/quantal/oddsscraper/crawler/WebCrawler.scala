package com.quantal.oddsscraper.crawler

import com.quantal.oddsscraper.traits.crawler._
import org.jsoup._;
import org.jsoup.nodes._
import org.jsoup.select.Elements
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import com.quantal.oddsscraper.models.database.MatchDataModel
import java.util.{Calendar, Locale, Date}
import java.text.SimpleDateFormat
import java.util
import com.quantal.oddsscraper.models.domain.Player

;

class WebCrawler extends TCrawler {

  private var htmlUnitDrv = new HtmlUnitDriver(true);
  var UserAgent = "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6"
  var Referrer = "http://www.google.com"
  override def crawl(listOfWebsites: List[String]): List[Document] = {
    var docsList: List[Document] = List[Document]();

    var htmlStr = ""
    listOfWebsites.foreach({
      website => {
        //htmlUnitDrv.get(website)

        //var doc: Document = Jsoup.connect(website).get()
        val doc = Jsoup.connect(website)
          .userAgent(UserAgent)
          .referrer(Referrer)
          .get()

        docsList = doc +: docsList

      }
    });

    processDocs(docsList)
    docsList
  }

  override def processDocs(listOfDocs: List[Document]) {

    if (listOfDocs != Nil) {
      listOfDocs.foreach(doc => {
        extractData(doc)
      });
    }
  }

  override def extractData(doc: Document):List[MatchDataModel]= {

    var docAsString = doc.toString
    var fixturesContainer = doc select "body div#fixtures"
    var tableRows: Elements = fixturesContainer.select("tbody > tr")
    var listOfMatchData: List[MatchDataModel] = List[MatchDataModel]()

    //val loopBreak = new Breaks
    var strDateAsStr: String = "";
    var strDateTimeFormat = "dd MMMM yyyy"
    var dateFormat: SimpleDateFormat = new SimpleDateFormat(strDateTimeFormat, Locale.ENGLISH)

    var bSkipMatchDetails = false;

    if (!tableRows.isEmpty) {
      //Get iterator to iterate throw rows in table
      var tblRowsIter = tableRows.iterator
      //iterate through the rows of match data
      while (tblRowsIter.hasNext) {
        var row = tblRowsIter.next
        var matchData = new MatchDataModel
        var matchDate = new Date;

        //Get the match date
        if (row.hasClass("date") && row.hasClass("first")) {
          strDateAsStr = row.text.substring(row.text.indexOf(' ')).trim
          var strDateAsStrAsArray = strDateAsStr.split(' ');
          val dayInDate = strDateAsStrAsArray(0)
          val suffix = getSuffix(dayInDate)
          strDateAsStr = strDateAsStr.replaceAll(suffix, "")
          strDateTimeFormat = "dd MMMM yyyy"
          dateFormat = new SimpleDateFormat(strDateTimeFormat, Locale.ENGLISH)
          matchDate = dateFormat.parse(strDateAsStr);
          bSkipMatchDetails = true

        }

        //Get the rows of matches for a specific date
        if (row.attr("data-market-id") != null) {

          var rowDataCells = row.select("td")
          matchData.MatchDate = matchDate

          //Get an iterator to iterate through the cells in a row
          var rowDataCellsIter = rowDataCells.iterator

          //Set some flags to control when to set the data
          var bAwayTeamSet = false
          var bHomeTeamSet = false
          var bSkipAwayTeam = false
          var bDrawSet = false;

          //Iterate through the row cells
          if (rowDataCellsIter != null)
            while (rowDataCellsIter.hasNext) {
              var rowCell = rowDataCellsIter.next

              //Set the time of the match
              if (rowCell.hasClass("time")) {
                val strDateTime = strDateAsStr + " " + rowCell.text
                dateFormat = new SimpleDateFormat(strDateTimeFormat + " HH:mm", Locale.ENGLISH)
                matchData.MatchTime = dateFormat.parse(strDateTime)
              }

              //Set the match details
              if (rowCell.attr("data-participant-id") != null) {

                //We skip setting the match details if we have just  obtained the the match date i.e. see code above where we bSkipMatchDetails set to true
                if (!bSkipMatchDetails) {
                  //Set the details for the home team
                  if (!bHomeTeamSet && !bAwayTeamSet && !rowCell.select(".fixtures-bet-name").isEmpty) {
                    matchData.HomeTeamName = rowCell.select(".fixtures-bet-name").text
                    matchData.HomeOdds = rowCell.select(".odds").text()
                    bHomeTeamSet = true
                    bSkipAwayTeam = true
                  }

                  //set the details of the odds of a draw

                  if (!bDrawSet &&
                    (!rowCell.select("[data-name]").isEmpty && rowCell.select("[data-name]").attr("data-name").trim.toLowerCase.equals("draw"))
                     || !rowCell.select(".fixtures-bet-name").isEmpty && rowCell.select(".fixtures-bet-name").text.trim.toLowerCase.equals("draw")) {
                    //rowData.DrawOdds =  rowCell.select("data-name").text().trim();
                    matchData.DrawOdds = rowCell.select(".odds").text()
                    bDrawSet = true
                    bSkipAwayTeam = true
                  }

                  //Set the details of the away details

                  if (!bSkipAwayTeam && !bAwayTeamSet && !rowCell.select(".fixtures-bet-name").isEmpty) {
                    matchData.AwayTeamName = rowCell.select(".fixtures-bet-name").text
                    matchData.AwayOdds = rowCell.select(".odds").text()
                    bAwayTeamSet = true
                  }
                  bSkipAwayTeam = false;

                }

                bSkipMatchDetails = false;
              }

            }

        }
        listOfMatchData = listOfMatchData.+:(matchData)
      }


    }
    listOfMatchData
  }

  override def getTeamLineups(homeTeam:String, awayTeam:String, matchDate:Date):List[Player]={
    var teamLineup:List[Player] = Nil
    var dateAsCal = Calendar.getInstance
    dateAsCal.setTimeInMillis(matchDate.getTime)
    var matchYear =  dateAsCal.get(Calendar.YEAR)
    var matchMonth = dateAsCal.get(Calendar.MONTH)+1

    var lgStartYear = 0;
    var lgEndYear = 0;

    //check if the matchDate month is between August and Dec
    //Get the league start and end year
    if (matchMonth >= 8 && matchMonth <= 12){
      lgStartYear =  matchYear
      lgEndYear += matchYear
    }
    else{
      lgStartYear -=  matchYear
      lgEndYear = matchYear
    }
    var teamLineupUrl = "http://www.premierleague.com/en-gb/matchday/matches/"+lgStartYear+"-"+lgEndYear+"/epl.html/"+homeTeam+"-vs-"+awayTeam

    var doc = Jsoup.connect(teamLineupUrl)
        .userAgent(UserAgent)
        .referrer(Referrer)
        .get()

    //http://www.premierleague.com/en-gb/matchday/matches/2013-2014/epl.html/fulham-vs-aston-villa

    var homeTeamLineup:List[Player]  =  Nil
    var homeTeamSubs:List[Player]  =  Nil
    var awayTeamLineup:List[Player]  =  Nil
    var awayTeamSubs:List[Player]  =  Nil

    if (doc!=null && !doc.childNodes().isEmpty){
      var homeTeamPlayerStartingPlayersRows = doc.select("homeStarters tbody tr")
      var homeTeamPlayers = doc.select("homeStarters tbody tr")
      var awayTeamPlayers = doc.select("homeStarters tbody tr")
    }


    teamLineup
  }

  private def removeNumSuffix(numWithSuffix: String): String = {

    var numWithoutSuffix = ""
    if (numWithSuffix != null)
      if (numWithSuffix.length == 3)
        numWithoutSuffix = numWithSuffix.substring(0, 1)

    if (numWithSuffix.length == 4)
      numWithoutSuffix = numWithSuffix.substring(0, 2)

    numWithoutSuffix
  }

  private def getSuffix(numWithSuffix: String): String = {

    var suffix = ""
    if (numWithSuffix != null)
      if (numWithSuffix.length == 3)
        suffix = numWithSuffix.substring(1)

    if (numWithSuffix.length == 4)
      suffix = numWithSuffix.substring(2)

    suffix
  }
}
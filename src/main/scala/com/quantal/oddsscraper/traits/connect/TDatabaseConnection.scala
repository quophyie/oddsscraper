package com.quantal.oddsscraper.traits.connect

import com.datastax.driver.core.{Session, Cluster}

/**
 * Created with IntelliJ IDEA.
 * User: dman
 * Date: 26/09/2013
 * Time: 22:29
 * To change this template use File | Settings | File Templates.
 */
trait TDatabaseConnection {
  var DBCluster: Cluster
  var DBSession: Session

  def connectToCluster(node: String): Cluster

  def shutdownCluster: Unit

}

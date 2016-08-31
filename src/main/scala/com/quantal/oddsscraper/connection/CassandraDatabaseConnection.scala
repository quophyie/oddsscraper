package com.quantal.oddsscraper.connection

import com.quantal.oddsscraper.traits.connect._
import com.datastax.driver.core.{Metadata, Cluster, Session}

/**
 * Created with IntelliJ IDEA.
 * User: dman
 * Date: 26/09/2013
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
class CassandraDatabaseConnection(node: String) extends TDatabaseConnection {
  //private var cluster:Cluster = null;
  private var cluster: Cluster = Cluster.builder().addContactPoint(node).build();

  //var DBCluster = cluster;

  //override def DBCluster :Cluster = connectToCluster(node)//Cluster.builder().addContactPoint(node).build();
  //override def DBSession:Session = DBCluster.connect()
  override def DBCluster: Cluster = cluster

  //Cluster.builder().addContactPoint(node).build();
  override def DBSession: Session = DBCluster.connect()

  override def DBSession_=(DBSession: Session) = {
    if (DBSession == null)
      DBCluster.connect()
  }

  override def DBCluster_=(DBCluster: Cluster) = {
    if (DBCluster == null)
      cluster = this.connectToCluster(node)
  }


  override def connectToCluster(node: String): Cluster = {
    if (DBCluster == null)
      cluster = Cluster.builder().withPort(9042).addContactPoint(node).build();
    val metadata: Metadata = DBCluster.getMetadata();
    System.out.printf("Connected to cluster: %s\n", metadata.getClusterName())
    DBCluster
  }

  override def shutdownCluster() {
    DBCluster.shutdown
  }


}

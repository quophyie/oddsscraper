package com.quantal.oddsscraper.connection

/**
 * Created with IntelliJ IDEA.
 * User: dman
 * Date: 29/09/2013
 * Time: 19:06
 * To change this template use File | Settings | File Templates.
 */

import org.junit._
import Assert._
import com.datastax.driver.core.{Session, Cluster}

class CassandraDatabaseConnectionTest {

  private var cassandraConn: CassandraDatabaseConnection = null
  private var DBCluster: Cluster = null
  private var DBSession: Session = null
  private var node: String = null

  @Before
  def setUp() {

    node = "192.168.0.15"
    cassandraConn = new CassandraDatabaseConnection(node)
  }

  @Test
  def testConnectToCluster() {
    DBCluster = cassandraConn connectToCluster node
    assertNotNull(DBCluster)
  }

  @Test
  def testShutdownCluster() {
    DBCluster = cassandraConn connectToCluster node
    cassandraConn.shutdownCluster()
    // assertTrue(DBSession)
  }

  @Test
  def testExtractDataFromDoc() {

  }
}

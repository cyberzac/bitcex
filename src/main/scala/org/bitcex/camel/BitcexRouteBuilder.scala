package org.bitcex.camel

import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.scala.{Period, Frequency}
import javax.sql.rowset.serial.SerialDatalink

object BitcexRouteBuilder extends RouteBuilder {

  // Ecb currency rates
  val ecbUrl = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"
  ecbUrl.throttle(1 per 60*60 seconds) --> "xslt:///ecb.xsl" -->  "seda:ecbCurrency"

  // MtGox tickers
  val mtGoxTickerUrl = "https://mtgox.com/code/data/ticker.php"
  mtGoxTickerUrl.throttle(1 per 5*60 seconds) -->  "seda:mtGoxTicker"

  "direct:velocity" --> "velocity:index.vm"

//  "jetty:http://localhost:8877/" --> "seda:TickerActor" --> "velocity:index.html"
//  "servlet:///index" --> "seda:TickerActor" --> "velocity:index.html"

   // Buy
  //  "servlet:///buy" -->  "velocity:buy.vm" --> "smtps://smtp.gmail.com?username=martin.zachrison&password=knt72/Qk&to=zac@cyberzac.se"
}
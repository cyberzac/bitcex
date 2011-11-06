package org.bitcex.camel

import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.scala.{Period, Frequency}
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired


object BitcexRouteBuilder extends RouteBuilder {

  // Ecb currency rates
  val ecbUrl = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"
  ecbUrl.throttle(1 per 60*60*1000 ) --> "xslt:ecb.xsl" -->  "seda:ecbCurrency"

  // MtGox tickers
  val mtGoxTickerUrl = "https://mtgox.com/code/data/ticker.php"
  mtGoxTickerUrl.throttle(1 per 5 *60*1000) -->  "seda:mtGoxTicker"

  "servlet:///update" --> mtGoxTickerUrl --> "seda:mtGoxTicker"

//  "restlet:/admin/users/{id}" --> "bean:userAdminRestlet"

  "restlet:/demo/{id}?restletMethod=POST" transform(simple("Request type : ${header.CamelHttpMethod} and ID : ${header.id},  name ${header.name}"))
}
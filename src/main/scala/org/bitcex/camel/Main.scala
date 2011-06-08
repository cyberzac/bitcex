package org.bitcex.camel

import akka.actor.Actor._
import akka.camel.CamelServiceManager._

import org.slf4j.LoggerFactory
import akka.camel.CamelContextManager
import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.scala.Period

object Main {

  val log = LoggerFactory.getLogger("bitcex")

  def main(args: Array[String]) {

    log.info("Starting bitcex")

    startCamelService
    CamelContextManager.init()
    val context = CamelContextManager.context.get
    context.addRoutes(new RouteBuilder {
      "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml".delay(new Period(30000)) --> "xslt:ecb.xsl" --> "file:ecb.xml"
    })
    CamelContextManager start

    val buyer = actorOf[BuyActor]
    val mtGox = actorOf[MtGoxTickerActor]
    val currency = actorOf[GoogleCurrencyActor]

    buyer.start
    mtGox.start

    val template = CamelContextManager.mandatoryTemplate
    val response = template.requestBody("https://mtgox.com/code/data/ticker.php/", "")
    log.info("Response was : {}", response)

    while(true) {
//      val ticker = CamelContextManager.mandatoryTemplate.requestBody("https://mtgox.com/code/data/ticker.php")// mtGox !! null
      val ticker = mtGox !! null
      log.info(". {}", ticker)
      Thread.sleep(10*1000)
    }

  }

}
package org.bitcex.camel

import akka.actor.Actor._
import akka.camel.CamelServiceManager._

import org.slf4j.LoggerFactory
import akka.camel.CamelContextManager
import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.scala.{Frequency, Period}

object Main {

  val log = LoggerFactory.getLogger("bitcex")

  def main(args: Array[String]) {

    log.info("Starting bitcex")

    val buyerActor = actorOf[BuyActor]
    val bitcexActor = actorOf[BitcexActor]
    val mtGoxActor = actorOf(new MtGoxTickerActor(bitcexActor))

    startCamelService
    CamelContextManager.init()
    val context = CamelContextManager.context.get
    context.addRoutes(new RouteBuilder {
      "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml".throttle(new Frequency(1, new Period(5*60*1000))) --> "xslt:///ecb.xsl" -->  "seda:ecbCurrency"
    })
    CamelContextManager start

    //val currency = actorOf[GoogleCurrencyActor]

    buyerActor.start()
    mtGoxActor.start()
    bitcexActor.start()

    val template = CamelContextManager.mandatoryTemplate
    val response = template.requestBody("https://mtgox.com/code/data/ticker.php/", "")
    log.info("Response was : {}", response)

    while(true) {
//      val ticker = CamelContextManager.mandatoryTemplate.requestBody("https://mtgox.com/code/data/ticker.php")// mtGox !! null
      val ticker = mtGoxActor !! null
      log.info(". {}", ticker)
      Thread.sleep(10*1000)
    }

  }

}
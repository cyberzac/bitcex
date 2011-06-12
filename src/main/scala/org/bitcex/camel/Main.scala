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
    val tickerActor = actorOf[TickerActor]
    val velocityActor = actorOf[VelocityActor]
    val indexActor = actorOf(new IndexActor(tickerActor, velocityActor))
    val mtGoxActor = actorOf(new MtGoxTickerActor(tickerActor))
    val ecbCurrencyActor = actorOf(new EcbCurrencyActor(tickerActor))

    startCamelService
    CamelContextManager.init()
    val context = CamelContextManager.context.get
    context.addRoutes(BitcexRouteBuilder)
    CamelContextManager start

    buyerActor.start()
    tickerActor.start()
    velocityActor.start()
    indexActor.start()
    mtGoxActor.start()
    ecbCurrencyActor.start()
         /*
    val template = CamelContextManager.mandatoryTemplate
    val response = template.requestBody("https://mtgox.com/code/data/ticker.php/", "")
    log.info("Response was : {}", response)
       */
    while(true) {
//      val ticker = CamelContextManager.mandatoryTemplate.requestBody("https://mtgox.com/code/data/ticker.php")// mtGox !! null
    //  val ticker = mtGoxActor !! null
      log.info(".")
      Thread.sleep(5*60*1000)
    }

  }

}
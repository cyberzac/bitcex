package org.bitcex.camel

import akka.actor.Actor._
import akka.camel.CamelServiceManager._

import org.slf4j.LoggerFactory
import akka.camel.CamelContextManager

object Main {

  val log = LoggerFactory.getLogger("bitcex")

  def main(args: Array[String]) {

    log.info("Starting bitcex")

    val buyerActor = actorOf[TraderActor]
    val tickerActor = actorOf[TickerActor]
    val tickerProducerActor = actorOf[TickerProducerActor]
    val tickerConsumerActor = actorOf(new TickerConsumerActor(tickerActor, tickerProducerActor))
    val mtGoxActor = actorOf(new MtGoxTickerActor(tickerActor))
    val mtGoxProducer = actorOf(new MtGoxTickerProducer((mtGoxActor)))
    val ecbCurrencyActor = actorOf(new EcbCurrencyActor(tickerActor))

    startCamelService
    CamelContextManager.init()
    val context = CamelContextManager.context.get
    context.addRoutes(BitcexRouteBuilder)
    CamelContextManager.start

    buyerActor.start()
    tickerActor.start()
    tickerProducerActor.start()
    tickerConsumerActor.start()
    mtGoxActor.start()
    mtGoxProducer.start()
    ecbCurrencyActor.start()
    /*
 val template = CamelContextManager.mandatoryTemplate
 val response = template.requestBody("https://mtgox.com/code/data/ticker.php/", "")
 log.info("Response was : {}", response)
    */
    while (true) {
      //      val ticker = CamelContextManager.mandatoryTemplate.requestBody("https://mtgox.com/code/data/ticker.php")// mtGox !! null
      //  val ticker = mtGoxActor !! null
      log.info(".")
      Thread.sleep(5 * 60 * 1000)
    }

  }

}
package org.bitcex.camel

import akka.actor.Actor._
import akka.camel.CamelServiceManager._

import org.slf4j.LoggerFactory
import akka.camel.CamelContextManager

object Main {

  val log = LoggerFactory.getLogger("bitcex")

  def main(args: Array[String]) {

    log.info("Starting bitcex")

    startCamelService
    CamelContextManager.init()
    val context = CamelContextManager.context.get
    context.addRoutes(BitcexRouteBuilder)
    CamelContextManager start

    val actorStarter = new ActorStarter()
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
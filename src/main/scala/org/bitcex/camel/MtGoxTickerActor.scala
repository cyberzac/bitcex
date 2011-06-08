package org.bitcex.camel

import akka.actor.Actor
import akka.camel.{Message, Producer}
import org.slf4j.LoggerFactory
import net.liftweb.json._
import org.bitcex.MtGoxTicker

class MtGoxTickerActor extends Actor with Producer {
  val log = LoggerFactory.getLogger(getClass)

  val uri = "https://mtgox.com/code/data/ticker.php/?delay=20000"

  def endpointUri = uri

  log.info("MtGox actor started {}", uri)

  override protected def receiveAfterProduce = {
    case msg: Message => {
      val body = msg.bodyAs[String]
      log.info("Received raw: {}", body)
      val json = parse(body)
      //val ticker = (json \"ticker").extract[MtGoxTicker] // No go conversion error
      val ticker = MtGoxTicker(json)
      log.info("Case class {}", ticker)
      self.reply(ticker)
    }

  }
}



package org.bitcex.camel

import akka.actor.Actor
import akka.camel.{Message, Producer}
import net.liftweb.json._
import org.slf4j.LoggerFactory

class GoogleCurrencyActor extends Actor with Producer {
    val log = LoggerFactory.getLogger(getClass)

  val uri = "http://www.google.com/ig/calculator?hl=en&q=1USD=?SEK"

  def endpointUri = uri

  log.info("Currency actor started {}", uri)

  override protected def receiveAfterProduce = {

    case msg:Message => {
      val body = msg.bodyAs[String]
       log.info("Received raw: {}", body)
       val json = parse(body)
    }
  }
}
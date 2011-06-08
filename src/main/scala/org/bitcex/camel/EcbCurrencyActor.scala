package org.bitcex.camel

import akka.actor.Actor
import akka.camel.{Message, Producer}
import org.slf4j.LoggerFactory

class EcbCurrencyActor extends Actor with Producer {
    val log = LoggerFactory.getLogger(getClass)

  val uri = " http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"

  def endpointUri = uri

  log.info("EcbCurrency actor started {}", uri)


  override protected def receiveBeforeProduce = {

  }

  override protected def receiveAfterProduce = {

    case msg:Message => {
      val body = msg.bodyAs[String]
       log.info("Received raw: {}", body)

    }
  }
}
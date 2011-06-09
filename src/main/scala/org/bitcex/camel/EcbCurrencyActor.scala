package org.bitcex.camel

import akka.actor.Actor
import org.slf4j.LoggerFactory
import akka.camel.{Consumer, Message}
import java.io.InputStream
import xml.{Elem, XML}
import org.bitcex._
import org.joda.time.DateMidnight

class EcbCurrencyActor extends Actor with Consumer {
    val log = LoggerFactory.getLogger(getClass)


  log.info("EcbCurrency actor started {}")

  def endpointUri = "seda:ecbCurrency"


  def updateRates(ecbupdate: Elem): Receive = {
    case ticker : MtGoxTicker =>
  }

  def receive = {
    case msg: Message => {
      log.info("Received {}", msg.bodyAs[String])
      val update = XML.load(msg.bodyAs[InputStream])
      val ts = new DateMidnight(update \ "@timestamp")

      become(updateRates(update))
    }

    case msg => log.info("WTF {}",  msg)
  }

}
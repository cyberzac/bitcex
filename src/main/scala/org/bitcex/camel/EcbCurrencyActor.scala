package org.bitcex.camel

import org.slf4j.LoggerFactory
import akka.camel.{Consumer, Message}
import xml.{Elem, XML}
import org.bitcex._
import org.joda.time.DateMidnight
import akka.actor.{ActorRef, Actor}

class EcbCurrencyActor(tickerActor:ActorRef) extends Actor with Consumer {
  val log = LoggerFactory.getLogger(getClass)
  val currencySpread = 0.05

  log.info("EcbCurrency actor started {}")

  def endpointUri = "seda:ecbCurrency"

  def receive = {

    case msg: Message => {
      val body = msg.bodyAs[String]
      val update = XML.loadString(body)
      val converter = parseMessage(update)
      tickerActor ! converter
      // Todo save as file only if the timestamp has changed by sending to a file:route
    //  self.reply(body)
    }

    case msg => log.info("Ignored {}", msg)
  }

  def parseMessage(update: Elem): CurrencyConverter[USD, SEK] = {
    val ts = new DateMidnight(update \ "@timestamp" text)
    val usd = USD(BigDecimal(update \ "USD" text))
    val sek = SEK(BigDecimal(update \ "SEK" text))
    CurrencyConverter(usd, sek, currencySpread)
  }

}
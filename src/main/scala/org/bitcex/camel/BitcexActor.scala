package org.bitcex.camel

import akka.actor.Actor
import org.slf4j.LoggerFactory
import akka.camel.{Consumer, Message}
import java.io.InputStream
import xml.{Elem, XML}
import org.bitcex._
import org.joda.time.DateMidnight

class BitcexActor extends Actor with Consumer {
  val log = LoggerFactory.getLogger(getClass)

  log.info("Bitcex actor started {}")

  def endpointUri = "seda:ecbCurrency"

  def serve(converter: CurrencyConverter[USD, SEK]): Receive = {
    case msg: Message => {
        val update = XML.load(msg.bodyAs[InputStream])
        val converter = parseMessage(update)
        become(serve(converter))
      }

      case mtGoxTicker: MtGoxTicker => {
        val tickerSEK = mtGoxTicker.toTicker(converter)
        self.reply(tickerSEK)
      }

       case msg => log.info("Ignored {}", msg)
  }

  def receive = {

    case msg: Message => {
      val body = msg.bodyAs[String]
      val update = XML.loadString(body)
      val converter = parseMessage(update)
      become(serve(converter))
    }

    case msg => log.info("Ignored {}", msg)
  }

  def parseMessage(update: Elem): CurrencyConverter[USD, SEK] = {
    val ts = new DateMidnight(update \ "@timestamp" text)
    val usd = USD(BigDecimal(update \ "USD" text))
    val sek = SEK(BigDecimal(update \ "SEK" text))
    CurrencyConverter(usd, sek, 0.05)
  }

}
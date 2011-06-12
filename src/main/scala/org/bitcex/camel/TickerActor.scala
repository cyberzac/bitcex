package org.bitcex.camel

import akka.actor.Actor
import org.slf4j.LoggerFactory
import org.bitcex._
import akka.camel.{Consumer, Message}

class TickerActor extends Actor with Consumer {
  val log = LoggerFactory.getLogger(getClass)

  log.info("Ticker actor started {}")

  def endpointUri = "seda:tickerActor"

  def receive = receive(None, None)

  def receive(converter: Option[CurrencyConverter[USD, SEK]], ticker: Option[Ticker[SEK]]): Receive = {

    case newConverter: CurrencyConverter[USD, SEK] => {
      log.info("New currencyConverter {}", newConverter)
      become(receive(Some(newConverter), ticker))
    }

    case mtGoxTicker: MtGoxTicker => {
      val newTicker = mtGoxTicker.toTicker(converter)
      log.info("MtGox updated new ticker : {}", newTicker)
      become(receive(converter, newTicker))
    }

    case GetTicker(receiver) => {
      if (receiver.isDefined) {
        receiver.get forward ticker
      } else {
      self.reply(ticker)}
    }

    case msg => log.info("Ignored {}", msg)
  }


}
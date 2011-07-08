package org.bitcex.camel

import akka.actor.Actor
import org.slf4j.LoggerFactory
import org.bitcex._
import model._
import util.CurrencyConverter
import akka.camel.{Consumer, Message}
import org.springframework.stereotype.Component

@Component
class TickerActor extends Actor with Consumer {
  val log = LoggerFactory.getLogger(getClass)

  log.info("Ticker actor started {}")

  def endpointUri = "seda:tickerActor"

  def receive = receive(None, Some(Ticker(SEK(10), SEK(7), SEK(3))))

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

    case GetTicker(receiverOption) => {
      if (receiverOption.isDefined) {
        val receiver = receiverOption.get
        log.debug("Forwardning ticker to {}", receiver)
        receiver forward ticker
      } else {
      self.reply(ticker)}
    }

    case msg => log.info("Ignored {}", msg)
  }


}
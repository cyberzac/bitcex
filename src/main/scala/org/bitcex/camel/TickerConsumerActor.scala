package org.bitcex.camel

import org.slf4j.LoggerFactory
import akka.actor.{ActorRef, Actor}
import akka.camel.{Producer, Consumer, Message}
import org.bitcex.model.{SEK, Ticker}
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.bitcex.messages.GetTicker

@Component
@Autowired
class TickerConsumerActor(ticker: ActorRef, producer: ActorRef) extends Actor with Consumer {
  val log = LoggerFactory.getLogger(getClass)

  def endpointUri = "servlet:///tickers"

  log.info("Ticker started at {}", endpointUri)

  def receive = {
    case msg: Message => {
      ticker.forward(GetTicker(Some(producer)))
    }
    case msg => self.reply("Uh?" + msg)
  }
}

@Component
class TickerProducerActor extends Actor with Producer {
  val log = LoggerFactory.getLogger(getClass)

  def endpointUri = "velocity:tickers.vm"

  override protected def receiveBeforeProduce = {
    case Some(ticker: Ticker[SEK]) => {
      val headers = Map("ask" -> ticker.ask.rounded,
        "last" -> ticker.last.rounded,
        "bid" -> ticker.bid.rounded)
      Message("should be replaced by velocity template", headers)
    }
    case msg => log.info("Ignored messsage {}", msg)
  }
}
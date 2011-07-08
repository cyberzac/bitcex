package org.bitcex.camel

import akka.camel.{Message, Producer}
import net.liftweb.json._
import org.slf4j.LoggerFactory
import akka.actor.{ActorRef, Actor}
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

@Component
@Autowired
class MtGoxTickerProducer(mtGoxTicker:ActorRef) extends Actor with Producer {
    val log = LoggerFactory.getLogger(getClass)

  val uri = "https://mtgox.com/code/data/ticker.php"

  def endpointUri = uri

  log.info("MtGoxTickerProducer actor started {}", uri)

  override protected def receiveAfterProduce = {

    case msg:Message => {
        mtGoxTicker ! msg
    }
  }
}
package org.bitcex.camel

import org.slf4j.LoggerFactory
import net.liftweb.json._
import org.bitcex.MtGoxTicker
import akka.actor.{ActorRef, Actor}
import akka.camel.{Consumer, Message, Producer}

/**
 * Translates from json to MtGoxTicker case class
 */
class MtGoxTickerActor(bitcexActor:ActorRef) extends Actor with Consumer {
  val log = LoggerFactory.getLogger(getClass)

  def endpointUri = "seda:mtGoxTicker"

  log.info("MtGoxTicker actor started")

 def receive = {
    case msg: Message => {
      val body = msg.bodyAs[String]
      log.info("Received raw: {}", body)
      val json = parse(body)
      val mtGoxTicker = MtGoxTicker(json)
      bitcexActor ! mtGoxTicker
    }

  }
}



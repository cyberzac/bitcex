package org.bitcex.camel

import akka.actor.Actor
import akka.camel.{Consumer, Message}
import org.slf4j.LoggerFactory


class BuyActor extends Actor with Consumer {

  val log = LoggerFactory.getLogger(getClass)

  val uri = "http://localhost:8877/buy"

  def endpointUri = "jetty:" + uri


  log.info("Buyer started at {}", uri)


  def receive = {
    case Message(body, headers) => self.reply("You bought %s".format(body))
    case _ => self.reply("Uh?")
  }
}
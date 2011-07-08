package org.bitcex.camel

import akka.actor.Actor._
import org.slf4j.LoggerFactory
import akka.actor.TypedActor

/**
 * To be started by the Spring context
 */
class SpringMain {

  val log = LoggerFactory.getLogger(getClass)

  log.info("Starting bitcex (spring)")

  val buyerActor = TypedActor.newInstance(classOf[Buyer], classOf[ServletActor])
  val tickerActor = actorOf[TickerActor]
  val velocityActor = actorOf[VelocityActor]
  val indexActor = actorOf(new IndexActor(tickerActor, velocityActor))
  val mtGoxActor = actorOf(new MtGoxTickerActor(tickerActor))
  val mtGoxProducer = actorOf(new MtGoxTickerProducer((mtGoxActor)))
  val ecbCurrencyActor = actorOf(new EcbCurrencyActor(tickerActor))

  //buyerActor.start()
  tickerActor.start()
  velocityActor.start()
  indexActor.start()
  mtGoxActor.start()
  mtGoxProducer.start()
  ecbCurrencyActor.start()
}
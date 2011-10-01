package org.bitcex.camel

import akka.actor.Actor._
import org.slf4j.LoggerFactory
import akka.actor.TypedActor
import org.bitcex.userservice.{UserService, InMemoryUserService}

/**
 * To be started by the Spring context
 */
class SpringMain {

  val log = LoggerFactory.getLogger(getClass)

  log.info("Starting bitcex (spring)")

  val userService = TypedActor.newInstance(classOf[UserService], classOf[InMemoryUserService], 1000);
  val traderActor = TypedActor.newInstance(classOf[ServletTrader], new TraderActor(userService))
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
package org.bitcex.camel

import akka.actor.Actor._
import org.slf4j.LoggerFactory
import akka.actor.TypedActor
import org.bitcex.userservice.{UserService, InMemoryUserService}
import org.bitcex.admin.{UserAdmin, UserAdminRestlet}

/**
 * To be started by the Spring context
 */
class ActorStarter {

  val log = LoggerFactory.getLogger(getClass)

  //Change Restlet logging from JUL to slf4j
  System.setProperty("org.restlet.engine.loggerFacadeClass", "org.restlet.ext.slf4j.Slf4jLoggerFacade")

  log.info("Starting actors")

  val userServiceActor = TypedActor.newInstance(classOf[UserService], classOf[InMemoryUserService], 1000)
  val traderActor = TypedActor.newInstance(classOf[ServletTrader], new TraderActor(userServiceActor))
  val tickerActor = actorOf[TickerActor].start()
  val velocityActor = actorOf[TickerProducerActor].start()
  val indexActor = actorOf(new TickerConsumerActor(tickerActor, velocityActor)).start()
  val mtGoxActor = actorOf(new MtGoxTickerActor(tickerActor)).start()
  val mtGoxProducer = actorOf(new MtGoxTickerProducer((mtGoxActor))).start()
  val ecbCurrencyActor = actorOf(new EcbCurrencyActor(tickerActor)).start()
  val userAdmin = TypedActor.newInstance(classOf[UserAdmin], new UserAdminRestlet(userServiceActor), 1000)
}
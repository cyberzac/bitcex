package org.bitcex.camel

import akka.actor.Actor._
import org.slf4j.LoggerFactory
import akka.actor.TypedActor
import org.bitcex.userservice.{UserService, InMemoryUserService}
import org.bitcex.admin.{UserAdmin, UserAdminRestlet}

/**
 * To be started by the Spring context
 */
class SpringMain {

  val log = LoggerFactory.getLogger(getClass)

  //Change Restlet logging from JUL to slf4j
  System.setProperty("org.restlet.engine.loggerFacadeClass","org.restlet.ext.slf4j.Slf4jLoggerFacade")

  log.info("Starting bitcex (spring)")

  val userServiceActor = TypedActor.newInstance(classOf[UserService], classOf[InMemoryUserService], 1000)
  val traderActor = TypedActor.newInstance(classOf[ServletTrader], new TraderActor(userServiceActor))
  val tickerActor = actorOf[TickerActor]
  val velocityActor = actorOf[VelocityActor]
  val indexActor = actorOf(new IndexActor(tickerActor, velocityActor))
  val mtGoxActor = actorOf(new MtGoxTickerActor(tickerActor))
  val mtGoxProducer = actorOf(new MtGoxTickerProducer((mtGoxActor)))
  val ecbCurrencyActor = actorOf(new EcbCurrencyActor(tickerActor))

  val userAdmin =  TypedActor.newInstance(classOf[UserAdmin], new UserAdminRestlet(userServiceActor), 1000)

  tickerActor.start()
  velocityActor.start()
  indexActor.start()
  mtGoxActor.start()
  mtGoxProducer.start()
  ecbCurrencyActor.start()
}
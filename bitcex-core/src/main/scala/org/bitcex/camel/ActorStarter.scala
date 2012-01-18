/*
 * Copyright © 2012 Martin Zachrison.
 *
 *     This file is part of bitcex
 *
 *     bitcex is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     bitcex is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with bitcex.  If not, see <http://www.gnu.org/licenses/>.
 */

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
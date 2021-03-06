package org.bitcex.camel

import org.slf4j.LoggerFactory
import akka.actor.Actor._
import java.math.BigDecimal
import akka.actor.{ActorRef, TypedActor}
import org.springframework.beans.factory.annotation.Autowired
import org.bitcex.userservice.UserService
import org.bitcex.OrderBookActor
import org.bitcex.model._


// Todo cake pattern ?
@Autowired
class TraderActor(val userService: UserService) extends TypedActor with ServletTrader {
  val orderBookActor = actorOf[OrderBookActor[BTC, SEK]].start()
  var userActors = Map[User, ActorRef]()


  val log = LoggerFactory.getLogger(getClass)
  val badAuth = "wrong email/password"

  log.info("TraderActor started at")


  def buy(amountStr: String, priceStr: String, email: String, password: String): String = {
    val amount = new BigDecimal(amountStr)
    val price = new BigDecimal(priceStr)
    val user = getUserActor(Email(email), password).getOrElse(return badAuth)
    val order = BidOrderSEK(BTC(amount), SEK(price), user)
    orderBookActor ! order
    val reply = "Bid order created: %s" format order
    self.reply(reply)
    reply
  }

  def sell(amountStr: String, priceStr: String, email: String, password: String): String = {
    val amount = new BigDecimal(amountStr)
    val price = new BigDecimal(priceStr)
    val user = getUserActor(Email(email), password).getOrElse(return badAuth)
    val order = AskOrderSEK(BTC(amount), SEK(price), user)
    orderBookActor ! order
    val reply = "Ask order created: %s" format order
    self.reply(reply)
    reply
  }

  def getUserActor(email: Email, password: String): Option[ActorRef] = {
    val user = userService.findByEmail(email).getOrElse(return None)
    if (!user.password.equals(password)) {
      return None
    }
    val userRef = userActors.get(user)
    if (userRef.isDefined) {
      return userRef
    }
    val actorRef = actorOf(new UserActor(user, userService))
    userActors = userActors + (user -> actorRef)
    Some(actorRef)
  }

}

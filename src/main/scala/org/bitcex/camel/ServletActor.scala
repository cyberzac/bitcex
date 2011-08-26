package org.bitcex.camel

import org.slf4j.LoggerFactory
import akka.actor.Actor._
import akka.camel.consume
import org.apache.camel.Header
import java.math.BigDecimal
import org.bitcex._
import model._
import akka.actor.{ActorRef, TypedActor}

trait Trader {

  val userService: UserService

  @consume("servlet:///buy")
  def buy(@Header("amount") amount: String, @Header("price") price: String, @Header("email") userId: String, @Header("password") password: String): String

  @consume("servlet:///sell")
  def sell(@Header("amount") amount: String, @Header("price") price: String, @Header("email") userId: String, @Header("password") password: String): String

}

class ServletActor extends TypedActor with Trader {
  val matcherActor = actorOf[MatcherActor[BTC, SEK]].start
  var userActors = Map[User, ActorRef]()
  // Todo cake pattern or spring...
  val userService = new InMemoryUserService()
  val log = LoggerFactory.getLogger(getClass)
  val badAuth = "wrong email/password"

  log.info("Trader started at")


  def buy(amountStr: String, priceStr: String, email: String, password: String): String = {
    val amount = new BigDecimal(amountStr)
    val price = new BigDecimal(priceStr)
    val user = getUserActor(Email(email), password).getOrElse(return badAuth)
    val order = BidOrderSEK(BTC(amount), SEK(price), user)
    matcherActor ! order
    val reply = "Bid order created: %s" format order
    self.reply(reply)
    reply
  }

  @consume("servlet:///sell")
  def sell(amountStr: String, priceStr: String, email: String, password: String): String = {
    val amount = new BigDecimal(amountStr)
    val price = new BigDecimal(priceStr)
    val user = getUserActor(Email(email), password).getOrElse(return badAuth)
    val order = AskOrderSEK(BTC(amount), SEK(price), user)
    matcherActor ! order
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
    val actorRef = actorOf(new UserActor(user))
    userActors = userActors + (user -> actorRef)
    Some(actorRef)
  }

}

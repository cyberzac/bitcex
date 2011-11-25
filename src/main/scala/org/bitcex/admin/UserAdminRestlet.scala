package org.bitcex.admin

import org.bitcex.userservice.UserService
import org.bitcex.model.{User, SEK, BTC, UserId}
import akka.actor.TypedActor
import akka.camel.consume
import org.slf4j.LoggerFactory
import org.apache.camel.{Headers, Header}

class UserAdminRestlet(val userService: UserService) extends TypedActor with UserAdmin {

  val log = LoggerFactory.getLogger(getClass)

  @consume("restlet:/admin/users/{userId}?restletMethod=GET")
  def get(@Header("userId") userId: String): Option[User] = {
    val user = userService.findById(userId)
    log.debug("Replying to GET {}", user)
    user
  }

  @consume("restlet:/admin/users?restletMethod=POST")
  def create(@Header("name") name: String, @Header("email") email: String, @Header("password") password: String): UserId = {
    val user = userService.create(name, email, password)
    log.debug("Created user {}", user)
    user.id
  }

  //  @consume("restlet:/admin/users/{userId}/addBTC?restletMethod=PUT")
  def addBtc(@Header("userId") id: String, @Header("amount") amount: BigDecimal): BTC = {
    val user = userService.findById(UserId(id)).get
    val amountBTC = BTC(amount)
    val newAmount = amountBTC + user.btc
    val newUser = userService update user.copy(btc = newAmount)
    log.debug("Updated user BTC to {}", newUser)
    newAmount
  }

  //@consume("restlet:/admin/users/{userId}/addsek?restletMethod=PUT")
  def addSek(@Header("userId") id: String, @Header("amount") amount: BigDecimal): SEK = {
    val user = userService.findById(UserId(id)).get
    val amountSEK = SEK(amount)
    val newAmount = amountSEK + user.sek
    val newUser = userService update user.copy(sek = newAmount)
    log.debug("Updated user SEK to {}", newUser)
    newAmount
  }

}
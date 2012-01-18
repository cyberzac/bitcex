package org.bitcex.admin

import org.bitcex.userservice.UserService
import org.bitcex.model.{User, SEK, BTC, UserId}
import akka.actor.TypedActor
import org.slf4j.LoggerFactory
import org.apache.camel.Header

class UserAdminRestlet(val userService: UserService) extends TypedActor with UserAdmin {

  val log = LoggerFactory.getLogger(getClass)

  def get(userId: String): Option[User] = {
    log.debug("Request for user {}", userId)
    val user = userService.findById(userId)
    log.debug("Replying to GET {}", user)
    user
  }


  def create(name: String, email: String, password: String): UserId = {
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
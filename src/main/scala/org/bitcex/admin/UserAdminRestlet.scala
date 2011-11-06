package org.bitcex.admin

import org.bitcex.userservice.UserService
import org.bitcex.model.{User, SEK, BTC, UserId}
import org.apache.camel.{Consume, Header}
import akka.actor.TypedActor
import akka.camel.consume

class UserAdminRestlet(val userService: UserService) extends TypedActor with UserAdmin {

   @consume("restlet:/admin/users/{userId}?restletMethod=GET")
  def get(@Header("userId") userId: String): Option[User] = {
    userService.findById(userId)
  }

  @consume( "restlet:/admin/users?restletMethod=POST")
  def create(@Header("name") name: String, @Header("email") email: String, @Header("password") password: String): UserId = {
    userService.create(name, email, password).id
  }

//  @consume("restlet:/admin/users/{userId}/addBTC?restletMethod=PUT")
  def addBtc(@Header("userId") id: String, @Header("amount") amount: BigDecimal): BTC = {
    val user = userService.findById(UserId(id)).get
    val amountBTC = BTC(amount)
    val newAmount = amountBTC + user.btc
    userService update user.copy(btc = newAmount)
    newAmount
  }

  //@consume("restlet:/admin/users/{userId}/addsek?restletMethod=PUT")
  def addSek(@Header("userId") id: String, @Header("amount") amount: BigDecimal): SEK = {
    val user = userService.findById(UserId(id)).get
    val amountSEK = SEK(amount)
    val newAmount = amountSEK + user.sek
    userService update user.copy(sek = newAmount)
    newAmount
  }

}
package org.bitcex.admin

import akka.camel.consume
import org.apache.camel.Header
import org.bitcex.userservice.UserService
import org.bitcex.model.{User, SEK, BTC, UserId}

class UserAdminRestlet(val userService: UserService) {

  @consume("restlet:/admin/user/create?restletMethod=POST")
  def create(@Header("name") name: String, @Header("email") email: String, @Header("password") password: String): User = {
    userService.create(name, email, password)
  }

  @consume("restlet:/admin/user/{userId}/addBTC?restletMethod=PUT")
  def addBtc(@Header("userId") id: String, @Header("amount") amount: BigDecimal): BTC = {
    val user = userService.findById(UserId(id)).get
    val amountBTC = BTC(amount)
    val newAmount = amountBTC + user.btc
    userService update user.copy(btc = newAmount)
    newAmount
  }

  @consume("restlet:/admin/user/{userId}/addsek?restletMethod=PUT")
  def addSek(@Header("userId") id: String, @Header("amount") amount: BigDecimal): SEK = {
    val user = userService.findById(UserId(id)).get
    val amountSEK = SEK(amount)
    val newAmount = amountSEK + user.sek
    userService update user.copy(sek = newAmount)
    newAmount
  }

}
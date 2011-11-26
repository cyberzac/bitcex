package org.bitcex.admin

import org.bitcex.model.{User, SEK, BTC, UserId}
import akka.camel.consume
import org.apache.camel.Header

trait UserAdmin {

  @consume("restlet:/admin/users/{id}?restletMethod=GET")
  def get(@Header("id") userId: String): Option[User]

  @consume("restlet:/admin/users/?restletMethod=POST")
  def create(@Header("name") name: String, @Header("email") email: String, @Header("password") password: String): UserId

  def addBtc(id: String, amount: BigDecimal): BTC

  def addSek(id: String, amount: BigDecimal): SEK

}
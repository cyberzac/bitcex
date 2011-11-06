package org.bitcex.admin

import org.bitcex.model.{User, SEK, BTC, UserId}

trait UserAdmin {

  def get(userId: String): Option[User]

  def create(name: String, email: String, password: String): UserId

  def addBtc(id: String, amount: BigDecimal): BTC

  def addSek(id: String, amount: BigDecimal): SEK

}
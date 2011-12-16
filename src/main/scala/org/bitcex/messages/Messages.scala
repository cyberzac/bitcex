package org.bitcex.messages

import org.bitcex.model.{Password, Email, Name}

case object GetUserMsg

case object ListOrders

case class CreateUser(name: Name, email: Email, password: Password)


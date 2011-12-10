package org.bitcex.messages

import org.bitcex.model.{Password, Email, Name}

case object GetUserMsg

case class CreateUser(name: Name, email: Email, password: Password)


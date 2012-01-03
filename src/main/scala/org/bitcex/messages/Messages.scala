package org.bitcex.messages

import org.bitcex.model.{Password, Email, Name}
import akka.actor.ActorRef

case object GetUserMsg

case object ListOrders

case class CreateUser(name: Name, email: Email, password: Password)

case class GetTicker(receiver: Option[ActorRef] = None)
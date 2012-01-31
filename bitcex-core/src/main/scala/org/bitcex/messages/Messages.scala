package org.bitcex.messages

import org.bitcex.model._
import akka.actor.ActorRef


case object GetUserMsg

case object ListOrders

case class ListOrders(user: ActorRef)

case class CreateUser(name: Name, email: Email, password: Password)

case class Orders[A <: Currency[A], P <: Currency[P]](askOrders: List[AskOrder[A, P]], bidOrders: List[BidOrder[A, P]])

case class OrdersSEK(askOrders: List[AskOrderSEK], bidOrders: List[BidOrderSEK])


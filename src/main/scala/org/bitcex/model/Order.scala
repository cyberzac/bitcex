package org.bitcex.model

import akka.actor.ActorRef

abstract class Order[T, S] {
  val amount: Price[T]
  val price: Price[S]
  val userRef: ActorRef

  def total: S = price * amount.amount
}

case class AskOrderSEK(amount: BTC, price: SEK, userRef:ActorRef) extends Order[BTC, SEK] {
  val seller = userRef
}

case class BidOrderSEK(amount: BTC, price: SEK, userRef:ActorRef) extends Order[BTC, SEK] {
  val buyer = userRef
}


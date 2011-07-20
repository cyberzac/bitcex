package org.bitcex.model

import akka.actor.ActorRef

sealed abstract class Order[T <: Price[T], S <: Price[S]] {
  val amount: T
  val price: S
  val userRef: ActorRef

  def total: S = price * amount.amount

  def create(amount: T): Order[T, S]

}

abstract class AskOrder[T <: Price[T], S <: Price[S]] extends Order[T, S] {
  val sellerRef = userRef

  def create(amount: T): AskOrder[T, S]

}

abstract class BidOrder[T <: Price[T], S <: Price[S]] extends Order[T, S] {
  val buyerRef = userRef

  def create(amount: T): BidOrder[T, S]
}

case class AskOrderSEK(amount: BTC, price: SEK, userRef: ActorRef) extends AskOrder[BTC, SEK] {
  def create(amount: BTC) = AskOrderSEK(amount, price, sellerRef)
}

case class BidOrderSEK(amount: BTC, price: SEK, userRef: ActorRef) extends BidOrder[BTC, SEK] {
  def create(amount: BTC) = BidOrderSEK(amount, price, buyerRef)
}
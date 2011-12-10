package org.bitcex.model

import akka.actor.ActorRef

sealed abstract class Order[T <: Currency[T], S <: Currency[S]] {
  val amount: T
  val price: S
  val userRef: ActorRef
  val timestamp: Long

  def total: S = price * amount.amount

  def create(amount: T, timstamp: Long = System.currentTimeMillis()): Order[T, S]

}

abstract class AskOrder[T <: Currency[T], S <: Currency[S]] extends Order[T, S] {
  val sellerRef = userRef

  def create(amount: T, timestamp: Long = System.currentTimeMillis()): AskOrder[T, S]

}

abstract class BidOrder[T <: Currency[T], S <: Currency[S]] extends Order[T, S] {
  val buyerRef = userRef

  def create(amount: T, timestamp: Long = System.currentTimeMillis()): BidOrder[T, S]
}

case class AskOrderSEK(amount: BTC, price: SEK, userRef: ActorRef, timestamp: Long = System.currentTimeMillis()) extends AskOrder[BTC, SEK] {
  def create(amount: BTC, timestamp: Long = System.currentTimeMillis()) = AskOrderSEK(amount, price, sellerRef, timestamp)
}

case class BidOrderSEK(amount: BTC, price: SEK, userRef: ActorRef, timestamp: Long = System.currentTimeMillis()) extends BidOrder[BTC, SEK] {
  def create(amount: BTC, timestamp: Long = System.currentTimeMillis()) = BidOrderSEK(amount, price, buyerRef, timestamp)
}
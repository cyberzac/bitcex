package org.bitcex.model

import akka.actor.ActorRef

object Trade {

  def apply[T <: Currency[T], S <: Currency[S]](ask: AskOrder[T, S], bid: BidOrder[T, S], price: S): Trade[T, S] = {
    if (ask.price > bid.price) throw new IllegalArgumentException("Bid price must be equal or higher then ask")
    val amount = ask.amount.min(bid.amount)
    Trade(amount, price, ask.sellerRef, bid.buyerRef)
  }
}

// Todo need state for confirmations? How to ensure that there is funds to cover this for seller and buyer
case class Trade[T <: Currency[T], S <: Currency[S]](amount: T, price: S, sellerRef: ActorRef, buyerRef: ActorRef) {
  //val time = new DateTime
}
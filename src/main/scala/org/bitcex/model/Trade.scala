package org.bitcex.model

import org.joda.time.DateTime
import akka.actor.ActorRef

// Todo need state for confirmations? How to ensure that there is funds to cover this for seller and buyer
case class Trade[T <: Price[T], S <: Price[S]](amount:T, price:S, sellerRef:ActorRef, buyerRef:ActorRef) {
  val time = new DateTime
}
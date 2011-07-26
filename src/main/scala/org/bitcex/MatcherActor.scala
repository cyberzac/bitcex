package org.bitcex

import akka.actor.Actor
import model.{BidOrder, AskOrder, Price}

//Todo where to send the trades
class MatcherActor[T <: Price[T], S <: Price[S]] extends Actor {
  val matcher = new Matcher[T, S]()
  protected def receive = {

    case askOrder:AskOrder[T, S] => {
        val trades = matcher.matchOrder(askOrder)
    }

    case bidOrder:BidOrder[T, S] => {
        val trades = matcher.matchOrder(bidOrder)
    }
  }
}
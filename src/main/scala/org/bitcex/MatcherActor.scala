package org.bitcex

import akka.actor.Actor
import model.{AskOrder, Price, SEK, BTC}

//Todo where to send the trades
class MatcherActor[T <: Price[T], S <: Price[S]] extends Actor {
  val matcher = new Matcher[T, S]()
  protected def receive = {
    case askOrder:AskOrder[T, S] => {
      //  matcher.matchOrder(askOrder)
    }
  }
}
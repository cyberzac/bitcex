package org.bitcex

import akka.actor.Actor
import model.{SEK, BTC, AskOrderSEK}

//Todo where to send the trades
class MatcherActor extends Actor {
  val matcher = new Matcher[BTC, SEK]()
  protected def receive = {
    case askOrder:AskOrderSEK => {
        matcher.matchOrder(askOrder)
    }
  }
}
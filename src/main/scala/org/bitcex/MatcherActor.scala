package org.bitcex

import akka.actor.Actor
import model.{Order, AskOrderSEK}

class MatcherActor extends Actor {
  val matcher = new Matcher()
  protected def receive = {
    case askOrder:AskOrderSEK => {
        matcher.matchOrder(askOrder)
    }
  }
}
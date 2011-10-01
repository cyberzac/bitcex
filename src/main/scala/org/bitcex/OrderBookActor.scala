package org.bitcex

import akka.actor.Actor
import model.{Trade, BidOrder, AskOrder, Price}

class OrderBookActor[T <: Price[T], S <: Price[S]] extends Actor {
  val matcher = new OrderBook[T, S]()

  def sendTrades(trades: scala.List[Trade[T, S]]) {
    trades.foreach(trade => {
      trade.buyerRef ! trade
      trade.sellerRef ! trade
    })
  }

  protected def receive = {

    case askOrder: AskOrder[T, S] => {
      val trades = matcher.matchOrder(askOrder)
      sendTrades(trades)
    }

    case bidOrder: BidOrder[T, S] => {
      val trades = matcher.matchOrder(bidOrder)
      sendTrades(trades)
    }
  }
}
package org.bitcex

import akka.actor.Actor
import messages.{Orders, ListOrders}
import model.{Trade, BidOrder, AskOrder, Currency}

class OrderBookActor[A <: Currency[A], P <: Currency[P]] extends Actor {
  val orderBook = new OrderBook[A, P]()

  def sendTrades(trades: scala.List[Trade[A, P]]) {
    trades.foreach(trade => {
      trade.buyerRef ! trade
      trade.sellerRef ! trade
    })
  }

  protected def receive = {

    case askOrder: AskOrder[A, P] => {
      val trades = orderBook.matchOrder(askOrder)
      sendTrades(trades)
    }

    case bidOrder: BidOrder[A, P] => {
      val trades = orderBook.matchOrder(bidOrder)
      sendTrades(trades)
    }

    case ListOrders => {
      self.reply(Orders(orderBook.askOrders, orderBook.bidOrders))
    }

    case ListOrders(user) => {
      val asks = orderBook.askOrders.filter(_.sellerRef == user)
      val bids = orderBook.bidOrders.filter(_.buyerRef == user)
      self reply Orders(asks, bids)
    }
  }
}
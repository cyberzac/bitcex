package org.bitcex

import model._

class Matcher[T <: Price[T], S <: Price[S]] {
  var askOrders = List[AskOrder[T, S]]()
  var bidOrders = List[BidOrder[T, S]]()

  def matchOrder(askOrder: AskOrder[T, S]): List[Trade[T, S]] = {
    addAskOrder(askOrder)
    List()
  }


  def matchOrder(bid: BidOrder[T, S], trades: List[Trade[T, S]] = List()): List[Trade[T, S]] = {
    val askOption = askOrders.find(_.price <= bid.price)
    if (askOption.isEmpty) {
      addBidOrder(bid)
      return trades
    }
    val ask = askOption.get
    val amount = ask.amount.min(bid.amount)
    val price = (ask.price - bid.price) / 2 + bid.price
    val trade = Trade(amount, price, ask.sellerRef, bid.buyerRef)
    askOrders = askOrders.filterNot(_ == ask)
    val diff = ask.amount - bid.amount
    if (diff.amount > 0) {
      addAskOrder(ask.create(diff))
    }
    val newTrades = trade :: trades
    if (diff.amount < 0) {
      matchOrder(bid.create(-diff), newTrades)
    } else {
      newTrades
    }
  }

  private def addAskOrder(askOrder: AskOrder[T, S]): Unit = {
    askOrders = (askOrder :: askOrders).sortWith((s, t) => s.price < t.price)
  }

  private def addBidOrder(bid: BidOrder[T, S]): Unit = {
    bidOrders = (bid :: bidOrders).sortWith((s, t) => s.price > t.price)
  }


}
package org.bitcex

import model._

class OrderBook[A <: Currency[A], P <: Currency[P]] {
  var askOrders = List[AskOrder[A, P]]()
  var bidOrders = List[BidOrder[A, P]]()

  def matchOrder(askOrder: AskOrder[A, P]): List[Trade[A, P]] = matchOrder(askOrder, List()).reverse

  def matchOrder(askOrder: AskOrder[A, P], trades: List[Trade[A, P]] = List()): List[Trade[A, P]] = {
    val bidOptions = bidOrders.filter(_.price >= askOrder.price).sortWith((a, b) => a.timestamp.compareTo(b.timestamp) < 0)
    if (bidOptions.isEmpty) {
      addOrder(askOrder)
      return trades
    }
    val bidOrder = bidOptions.head
    val trade = Trade(askOrder, bidOrder, bidOrder.price)
    removeOrder(bidOrder)
    val diff = bidOrder.amount - askOrder.amount
    diff.signum match {
      case -1 => matchOrder(askOrder.create(-diff, askOrder.timestamp), trade :: trades)
      case 0 => trade :: trades
      case 1 => {
        addOrder(bidOrder.create(diff, bidOrder.timestamp))
        trade :: trades
      }
    }
  }

  def matchOrder(bidOrder: BidOrder[A, P]): List[Trade[A, P]] = matchOrder(bidOrder, List()).reverse

  private def matchOrder(bidOrder: BidOrder[A, P], trades: List[Trade[A, P]]): List[Trade[A, P]] = {
    val askOptions = askOrders.filter(_.price <= bidOrder.price).sortWith((a, b) => a.timestamp.compareTo(b.timestamp) < 0)
    if (askOptions.isEmpty) {
      addOrder(bidOrder)
      return trades
    }
    val askOrder = askOptions.head
    val trade = Trade(askOrder, bidOrder, askOrder.price)
    removeOrder(askOrder)
    val diff = askOrder.amount - bidOrder.amount
    diff.signum match {
      case -1 => {
        matchOrder(bidOrder.create(-diff, bidOrder.timestamp), trade :: trades)
      }
      case 0 => trade :: trades
      case 1 => {
        addOrder(askOrder.create(diff, askOrder.timestamp))
        trade :: trades
      }
    }
  }

  def removeOrder(ask: AskOrder[A, P]) {
    askOrders = askOrders.filterNot(_ == ask)
  }

  def removeOrder(bid: BidOrder[A, P]) {
    bidOrders = bidOrders.filterNot(_ == bid)
  }

  private def addOrder(askOrder: AskOrder[A, P]) {
    askOrders = (askOrder :: askOrders).sortWith((s, t) => s.price < t.price)
  }

  private def addOrder(bidOrder: BidOrder[A, P]) {
    bidOrders = (bidOrder :: bidOrders).sortWith((s, t) => s.price > t.price)
  }


}
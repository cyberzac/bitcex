package org.bitcex

import model._

class OrderBook[T <: Currency[T], S <: Currency[S]] {
  var askOrders = List[AskOrder[T, S]]()
  var bidOrders = List[BidOrder[T, S]]()

  def matchOrder(askOrder: AskOrder[T, S]): List[Trade[T, S]] = matchOrder(askOrder, List())

  def matchOrder(askOrder: AskOrder[T, S], trades: List[Trade[T, S]] = List()): List[Trade[T, S]] = {
    val bidOption = bidOrders.find(_.price >= askOrder.price)
    bidOption match {
      case Some(bidOrder) => {
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
      case None => {
        addOrder(askOrder)
        trades
      }
    }
  }

  def matchOrder(bidOrder: BidOrder[T, S]): List[Trade[T, S]] = matchOrder(bidOrder, List()).reverse

  private def matchOrder(bidOrder: BidOrder[T, S], trades: List[Trade[T, S]]): List[Trade[T, S]] = {
    val askOptions = askOrders.filter(_.price <= bidOrder.price).sortWith((a, b) => a.timestamp.compareTo(b.timestamp) < 0)
    if (askOptions.isEmpty) {
      addOrder(bidOrder)
      return trades
    }
    val askOrder = askOptions.head
    val trade = Trade(askOrder, bidOrder, askOrder.price)
    removeOrder(askOrder)
    val amountDiff = askOrder.amount - bidOrder.amount
    amountDiff.signum match {
      case -1 => {
        matchOrder(bidOrder.create(-amountDiff, bidOrder.timestamp), trade :: trades)
      }
      case 0 => trade :: trades
      case 1 => {
        addOrder(askOrder.create(amountDiff, askOrder.timestamp))
        trade :: trades
      }
    }
  }

  def removeOrder(ask: AskOrder[T, S]) {
    askOrders = askOrders.filterNot(_ == ask)
  }

  def removeOrder(bid: BidOrder[T, S]) {
    bidOrders = bidOrders.filterNot(_ == bid)
  }

  private def addOrder(askOrder: AskOrder[T, S]) {
    askOrders = (askOrder :: askOrders).sortWith((s, t) => s.price < t.price)
  }

  private def addOrder(bidOrder: BidOrder[T, S]) {
    bidOrders = (bidOrder :: bidOrders).sortWith((s, t) => s.price > t.price)
  }


}
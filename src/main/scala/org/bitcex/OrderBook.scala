package org.bitcex

import model._

class OrderBook[T <: Price[T], S <: Price[S]] {
  var askOrders = List[AskOrder[T, S]]()
  var bidOrders = List[BidOrder[T, S]]()

  def matchOrder(askOrder: AskOrder[T, S]): List[Trade[T, S]] = matchOrder(askOrder, List())

  def matchOrder(askOrder: AskOrder[T, S], trades: List[Trade[T, S]] = List()): List[Trade[T, S]] = {
    val bidOption = bidOrders.find(_.price >= askOrder.price)
    bidOption match {
      case Some(bidOrder) => {
        val trade = Trade(askOrder, bidOrder)
        removeOrder(bidOrder)
        val diff = bidOrder.amount - askOrder.amount
        diff.signum match {
          case -1 => matchOrder(askOrder.create(-diff), trade :: trades)
          case 0 => trade :: trades
          case 1 => {
            addOrder(bidOrder.create(diff))
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

  def matchOrder(bidOrder: BidOrder[T, S]): List[Trade[T, S]] = matchOrder(bidOrder, List())

  def matchOrder(bidOrder: BidOrder[T, S], trades: List[Trade[T, S]]): List[Trade[T, S]] = {
    val askOption = askOrders.find(_.price <= bidOrder.price)
    if (askOption.isEmpty) {
      addOrder(bidOrder)
      return trades
    }
    val askOrder = askOption.get
    val trade = Trade(askOrder, bidOrder)
    removeOrder(askOrder)
    val diff = askOrder.amount - bidOrder.amount
    diff.signum match {
      case -1 => {
        matchOrder(bidOrder.create(-diff), trade :: trades)
      }
      case 0 => trade :: trades
      case 1 =>  {
        addOrder(askOrder.create(diff))
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

  private def addOrder(askOrder: AskOrder[T, S]): Unit = {
    askOrders = (askOrder :: askOrders).sortWith((s, t) => s.price < t.price)
  }

  private def addOrder(bidOrder: BidOrder[T, S]): Unit = {
    bidOrders = (bidOrder :: bidOrders).sortWith((s, t) => s.price > t.price)
  }


}
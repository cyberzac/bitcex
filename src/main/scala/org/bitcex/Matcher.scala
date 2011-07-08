package org.bitcex

import model.{Trade, BidOrderSEK, AskOrderSEK}

class Matcher {
  var askOrders = List[AskOrderSEK]()
  var bidOrders = List[BidOrderSEK]()

  def matchOrder(askOrder:AskOrderSEK): Option[Trade] = {
    askOrders = (askOrder :: askOrders).sortWith((s,t) => s.price < t.price)
    None
  }

  def matchOrder(bid:BidOrderSEK): Option[Trade] = {
    val askOption = askOrders.find((a) => a.price <= bid.price)
    if (askOption.isEmpty) {
      bidOrders = (bid :: bidOrders).sortWith((s,t) => s.price > t.price)
     return None
    }
    val ask = askOption.get
    val amount = ask.amount.min(bid.amount)
    val price =  (ask.price - bid.price) / 2 + bid.price
    Some(Trade(amount, price, ask, bid))
  }
}
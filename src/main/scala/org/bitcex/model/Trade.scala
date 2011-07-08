package org.bitcex.model

import org.joda.time.DateTime


case class Trade(amount:BTC, price:SEK, askOrder:AskOrderSEK, bidOrder:BidOrderSEK) {
  val time = new DateTime
}
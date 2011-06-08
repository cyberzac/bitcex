package org.bitcex

import akka.event.slf4j.Logging
import akka.actor.{ActorRef, Actor}


/*
class Account(val id:String, val router:ActorRef, var sek: SEK, var btc: BTC) extends Actor with Logging {

  protected def receive = {

    case so@SellOrder(sell, price) =>  {
      log.info("Sell Order for %d BTC", sell)
      if (sell.amount > btc.amount) {
        log.error("Not enough BTC")
      }
      btc -= sell
      router ! so
    }

     case bo@BuyOrder(buy, price) =>  {
      log.info("Buy Order for %d BTC", buy)
      if (bo.total > sek) {
        log.error("Not enough SEK")
      }
      sek -= bo
      router ! bo
    }

    case CancelSellOrder(sell) => {
      btc += sell
    }
  }



}
*/
package org.bitcex

import net.liftweb.json.DefaultFormats
import net.liftweb.json.JsonAST.JValue

object MtGoxTicker {
    implicit val formats = DefaultFormats
  /**
   * Construct from Json
   */
  def apply(json: JValue) = {
    val high = extractUSD("high", json)
    val low = extractUSD("low", json)
    val vol = extractBigDecimal("vol", json)
    val buy = extractUSD("buy", json)
    val sell = extractUSD("sell", json)
    val last = extractUSD("last", json)
    new MtGoxTicker(high, low, vol, buy, sell, last)
  }
  def extractBigDecimal(field:String,  json: JValue): BigDecimal = BigDecimal((json \\ field).extract[Double])
  def extractUSD(field:String, json:JValue) : USD = USD(extractBigDecimal(field, json))
}

  case class MtGoxTicker(high: USD, low: USD, vol: BigDecimal, bid: USD, ask: USD, last: USD) {
    def toTicker[T <:Fund[T]](converter:CurrencyConverter[USD, T]): Ticker[T] = {
      val lastSEK = converter.midpoint(last)
      val askSEK = converter.bid(ask)
      val bidSEK = converter.ask(bid)
      Ticker(ask=askSEK, last=lastSEK, bid=bidSEK)
    }
  }
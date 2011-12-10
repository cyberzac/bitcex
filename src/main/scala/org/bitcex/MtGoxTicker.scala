package org.bitcex

import model.{Currency, Ticker, USD}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.JsonAST.JValue
import util.CurrencyConverter

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

  def extractBigDecimal(field: String, json: JValue): BigDecimal = BigDecimal((json \\ field).extract[Double])

  def extractUSD(field: String, json: JValue): USD = USD(extractBigDecimal(field, json))
}

case class MtGoxTicker(high: USD, low: USD, vol: BigDecimal, bid: USD, ask: USD, last: USD) {
  def toTicker[T <: Currency[T]](converterOption: Option[CurrencyConverter[USD, T]]): Option[Ticker[T]] = {
    val converter = converterOption.getOrElse(return None)
    val lastSEK = converter.midpoint(last)
    val askSEK = converter.bid(ask)
    val bidSEK = converter.ask(bid)
    Some(Ticker(ask = askSEK, last = lastSEK, bid = bidSEK))
  }
}
package org.bitcex.model

case class Ticker[T <:Currency[T]] (ask:T, last:T, bid:T)

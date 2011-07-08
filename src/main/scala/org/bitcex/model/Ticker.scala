package org.bitcex.model

case class Ticker[T <:Price[T]] (ask:T, last:T, bid:T)

package org.bitcex.model

import org.eclipse.jetty.server.UserIdentity

sealed abstract class Price[T] {
  val amount: BigDecimal

  def create(amount: BigDecimal): T

  def >(fund: Price[T]) = amount > fund.amount

  def >=(fund: Price[T]) = amount >= fund.amount

  def <(fund: Price[T]) = amount < fund.amount

  def <=(fund: Price[T]) = amount <= fund.amount

  def -(fund: Price[T]) = create(amount - fund.amount)

  def +(fund: Price[T]) = create(amount + fund.amount)

  def *(factor: BigDecimal):T = create(amount * factor)

  def *(fund:Price[T]):T = fund * amount

  def /(factor: BigDecimal):T = create(amount / factor)

  def /(fund:Price[T]):T = this / fund.amount

  def min(fund:Price[T]) = create(amount.min(fund.amount))

  def max(fund:Price[T]) = create(amount.max(fund.amount))

  def rounded = "%.2f".format(amount)

}


case class SEK(amount: BigDecimal) extends Price[SEK] {
  def create(amount: BigDecimal) = copy(amount);
}

case class EUR(amount: BigDecimal) extends Price[EUR] {
  def create(amount: BigDecimal) = copy(amount);
}

case class USD(amount: BigDecimal) extends Price[USD] {
  def create(amount: BigDecimal) = copy(amount);
}

case class BTC(amount: BigDecimal) extends Price[BTC] {
  def create(amount: BigDecimal) = copy(amount)
}





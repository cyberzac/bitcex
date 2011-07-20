package org.bitcex.model

sealed abstract class Price[T] {
  val amount: BigDecimal

  def create(amount: BigDecimal): T

  def >(price: Price[T]):Boolean = amount > price.amount

  def >=(price: Price[T]):Boolean = amount >= price.amount

  def <(price: Price[T]):Boolean = amount < price.amount

  def <=(price: Price[T]):Boolean = amount <= price.amount

  def -(price: Price[T]):T = create(amount - price.amount)

  def +(price: Price[T]):T = create(amount + price.amount)

  def *(factor: BigDecimal):T = create(amount * factor)

  def *(price:Price[T]):T = price * amount

  def /(factor: BigDecimal):T = create(amount / factor)

  def /(price:Price[T]):T = this / price.amount

  def unary_-():T = create(-amount)

  def signum:Int = amount.signum

  def min(price:Price[T]) = create(amount.min(price.amount))

  def max(price:Price[T]) = create(amount.max(price.amount))

  def rounded:String = "%.2f".format(amount)

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





package org.bitcex.model

sealed abstract class Currency[T] {
  // self: { def copy(amount:BigDecimal) : T } =>
  // def copy(amount:BigDecimal): T

  val amount: BigDecimal

  def create(amount: BigDecimal): T //= this.copy(amount)

  def >(price: Currency[T]): Boolean = amount > price.amount

  def >=(price: Currency[T]): Boolean = amount >= price.amount

  def <(price: Currency[T]): Boolean = amount < price.amount

  def <=(price: Currency[T]): Boolean = amount <= price.amount

  def -(price: Currency[T]): T = create(amount - price.amount)

  def +(price: Currency[T]): T = create(amount + price.amount)

  def *(factor: BigDecimal): T = create(amount * factor)

  def *(price: Currency[T]): T = price * amount

  def /(factor: BigDecimal): T = create(amount / factor)

  def /(price: Currency[T]): T = this / price.amount

  def unary_-(): T = create(-amount)

  def signum: Int = amount.signum

  def min(price: Currency[T]) = create(amount.min(price.amount))

  def max(price: Currency[T]) = create(amount.max(price.amount))

  def rounded: String = "%.2f".format(amount)

}


case class SEK(amount: BigDecimal) extends Currency[SEK] {
  def create(amount: BigDecimal) = copy(amount);
}

case class EUR(amount: BigDecimal) extends Currency[EUR] {
  def create(amount: BigDecimal) = copy(amount);
}

case class USD(amount: BigDecimal) extends Currency[USD] {
  def create(amount: BigDecimal) = copy(amount);
}

case class BTC(amount: BigDecimal) extends Currency[BTC] {
  def create(amount: BigDecimal) = copy(amount)
}





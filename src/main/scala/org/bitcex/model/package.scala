package org.bitcex

package object model {

  implicit def stringToName(name:String) = Name(name)
  implicit def stringToEmail(email:String) = Email(email)
  implicit def stringToPassword(clear:String) = Password(clear)
  implicit def stringToUserId(userId:String) = UserId(userId)

  implicit def bigDecimalToBTC(amount:BigDecimal) = BTC(amount)
  implicit def bigDecimalToEUR(amount:BigDecimal) = EUR(amount)
  implicit def bigDecimalToSEK(amount:BigDecimal) = SEK(amount)
  implicit def bigDecimalToUSD(amount:BigDecimal) = USD(amount)

  implicit def stringToBTC(amount:String) = BTC(BigDecimal(amount))
  implicit def stringToEUR(amount:String) = EUR(BigDecimal(amount))
  implicit def stringToSEK(amount:String) = SEK(BigDecimal(amount))
  implicit def stringToUSD(amount:String) = USD(BigDecimal(amount))
}
package org.bitcex.model

object User {
  //Todo replace with implicits for the arguments
  def apply(name:String, email:String, id:String, password:String, sek:SEK, btc:BTC):User =  User(Name(name), Email(email), UserId(id), Password(password), sek, btc)
}

case class User(name:Name, email:Email, id:UserId, password:Password, sek:SEK=SEK(0), btc:BTC=BTC(0))

case class Email(value:String)

case class Name(value:String)

case class UserId(value:String)

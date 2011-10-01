package org.bitcex.model

case class User(name:Name, email:Email, id:UserId, password:Password, sek:SEK=SEK(0), btc:BTC=BTC(0))

case class Email(value:String)

case class Name(value:String)

case class UserId(value:String)


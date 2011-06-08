package org.bitcex.camel

import akka.actor.Actor
import akka.camel.{Message, Producer}
import net.liftweb.json.parse
import org.bitcex.{EUR, SEK, CurrencyConverter}

class GoogleCurrencySEKEUR extends Actor with Producer {

  val uri = "http://www.google.com/ig/calculator?q=1EUR%3D%3FSEK"

   def endpointUri = uri

  override protected def receiveAfterProduce = {
    case msg:Message => {
      val body = msg.bodyAs[String]
      val json = parse(body)
       val sek = 6.14
      CurrencyConverter(EUR(1) , SEK(sek))
    }
  }
}
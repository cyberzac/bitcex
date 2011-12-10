package org.bitcex.camel

import akka.camel.consume
import org.apache.camel.Header

trait ServletTrader {


  @consume("servlet:///buy")
  def buy(@Header("amount") amount: String, @Header("price") price: String, @Header("email") userId: String, @Header("password") password: String): String

  @consume("servlet:///sell")
  def sell(@Header("amount") amount: String, @Header("price") price: String, @Header("email") userId: String, @Header("password") password: String): String

}



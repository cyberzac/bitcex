package org.bitcex.model

import org.specs2.mutable.Specification


class packageSpec extends Specification {

  "The implicit conversion for  User" should {
    "convert a string to a Name" in {
      val nameString = "Martin Zachrison"
      val name: Name = nameString
      name must_== Name(nameString)
    }
    "convert a string to an Email" in {
      val emailString = "mail@internet.net"
      val email: Email = emailString
      email must_== Email(emailString)
    }

    "convert a string to a Password" in {
      val clear = "clear"
      val password: Password = clear
      password.equals(clear) must beTrue
    }
  }
  "convert a string to an UserId" in {
    val userIdString = "userId"
    val userId: UserId = userIdString
    userId must_== UserId(userIdString)
  }

  "The implicit conversions for Price" should {
    "convert a BigDecimal to BTC" in {
      val bigDecimal: BigDecimal = 1.5
      val btc: BTC = bigDecimal
      btc must_== BTC(bigDecimal)
    }
    "convert a BigDecimal to EUR" in {
      val bigDecimal: BigDecimal = 1.5
      val eur: EUR = bigDecimal
      eur must_== EUR(bigDecimal)
    }
    "convert a BigDecimal to SEK" in {
      val bigDecimal: BigDecimal = 1.5
      val sek: SEK = bigDecimal
      sek must_== SEK(bigDecimal)
    }
    "convert a BigDecimal to USD" in {
      val bigDecimal: BigDecimal = 1.5
      val usd: USD = bigDecimal
      usd must_== USD(bigDecimal)
    }
    "convert a string to BTC" in {
      val string = "1.5"
      val btc: BTC = string
      btc must_== BTC(1.5)
    }
    "convert a string to EUR" in {
      val string = "1.5"
      val eur: EUR = string
      eur must_== EUR(1.5)
    }
    "convert a string to SEK" in {
      val string = "1.5"
      val sek: SEK = string
      sek must_== SEK(1.5)
    }
    "convert a string to USD" in {
      val string = "1.5"
      val usd: USD = string
      usd must_== USD(1.5)
    }

  }
}
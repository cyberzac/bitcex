package org.bitcex

import org.specs.Specification


class ModelSpec extends Specification {

  "A SEK fund " should {

    "Have an > comparison method " in {
      SEK(10) > SEK(9) must beTrue
    }

    "Have an == method" in {
      SEK(3.4) == SEK(3.4) must beTrue
    }

    "Have an < method" in {
      SEK(5) < SEK(5.0000001) must beTrue
    }

    "Have a - method" in {
      SEK(5) - SEK(1) must_== SEK(4)
    }

    "Have a + method" in {
      SEK(5) + SEK(1) must_== SEK(6)
    }

  }

  "SellOrder" should {
    "Have a total" in {
      AskOrderSEK(BTC(10), SEK(5)).total must_== SEK(50)
    }
  }

  "A CurrencyConverter[SEK,USD]" should {
    val convertUSD_SEK = CurrencyConverter(USD(1), SEK(6.5), spread = 0.02)
    "Buy USD for SEK" in {
      convertUSD_SEK.ask(USD(2)) must_== SEK(12.74)
    }
    "Sell USD for SEK" in {
      convertUSD_SEK.bid(USD(2)) must_== SEK(13.26)
    }
    "Sell SEK for USD" in {
      convertUSD_SEK.inverseBid(SEK(12.74)) must_== USD(2)
    }
    "Buy SEK for USD" in {
      convertUSD_SEK.inverseAsk(SEK(13.26)) must_== USD(2)
    }

    "Have a midpoint rate" in {
      convertUSD_SEK.midpoint(USD(1)) must_== SEK(6.5)
    }
  }

}
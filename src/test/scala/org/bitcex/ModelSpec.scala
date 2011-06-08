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
      SellOrderSEK(BTC(10), SEK(5)).total must_== SEK(50)
    }
  }

  "A CurrencyConverter[SEK,USD]" should {
    val convertUSD_SEK = CurrencyConverter(USD(1), SEK(6.5), spread = 0.02)
    "I buy USD for SEK" in {
      convertUSD_SEK.ask(USD(2)) must_== SEK(12.74)
    }
    "I sell USD for SEK" in {
      convertUSD_SEK.bid(USD(2)) must_== SEK(13.26)
    }
    "I sell SEK for USD" in {
      convertUSD_SEK.inverseBid(SEK(12.74)) must_== USD(2)
    }
    "I buy SEK for USD" in {
      convertUSD_SEK.inverseAsk(SEK(13.26)) must_== USD(2)
    }


    "Convert from SEK to USD" in {
      convertUSD_SEK.midpoint(SEK(26)) must_== USD(4)
    }
  }

}
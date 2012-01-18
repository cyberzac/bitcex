package org.bitcex.util

import org.specs.Specification
import org.bitcex.model.{SEK, USD}

class CurrencyConverterSpec extends Specification {


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
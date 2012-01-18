package org.bitcex

import model.{SEK, USD}
import org.specs.Specification
import net.liftweb.json.parse
import util.CurrencyConverter

class MtGoxTickerSpec extends Specification {

  "MtGoxTicker json extraction" should {

    val mtGoxJson = """{"ticker":{"high":18.998,"low":17.0015,"vol":23517,"buy":17.158,"sell":17.16,"last":17.159}}"""
    val json = parse(mtGoxJson)
    val ticker = MtGoxTicker(json)

    "Extract high" in { ticker.high must_== USD(18.998) }
    "Extract low" in { ticker.low must_== USD(17.0015) }
    "Extract volume" in { ticker.vol must_== BigDecimal(23517) }
    "Extract buy" in { ticker.bid must_== USD(17.158) }
    "Extract sell" in { ticker.ask must_== USD(17.16) }
    "Extract last" in { ticker.last must_== USD(17.159) }

  }

  "MtGoxTicker convert to Ticker" should {

    val mtGoxTicker = MtGoxTicker(USD(10), USD(1), 5, USD(3), USD(7), USD(5))
    val converter = Some(CurrencyConverter(USD(1), SEK(100), 0.01))
    val tickerOption = mtGoxTicker.toTicker(converter)
    val ticker = tickerOption.get


    "Last price" in { ticker.last must_== SEK(500) }
    "Ask price is ask plus spread" in { ticker.ask must_== SEK(707) }
     "Bid price is bid minus spread " in{ ticker.bid must_== SEK(297)}
    "return None if the converter is None" in { mtGoxTicker.toTicker(None) must_==  None }
  }
}
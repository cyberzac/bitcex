package org.bitcex

import org.specs.Specification
import net.liftweb.json.parse

class MtGoxTickerSpec extends Specification {

  "MtGoxTicker json extraction" should {

    val mtGoxJson = """{"ticker":{"high":18.998,"low":17.0015,"vol":23517,"buy":17.158,"sell":17.16,"last":17.159}}"""
    val json = parse(mtGoxJson)
    val ticker = MtGoxTicker(json)

    "Extract high" in { ticker.high must_== USD(18.998) }
    "Extract low" in { ticker.low must_== USD(17.0015) }
    "Extract volume" in { ticker.vol must_== BigDecimal(23517) }
    "Extract buy" in { ticker.buy must_== USD(17.158) }
    "Extract sell" in { ticker.sell must_== USD(17.16) }
    "Extract last" in { ticker.last must_== USD(17.159) }

  }
}
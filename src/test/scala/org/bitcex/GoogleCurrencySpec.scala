package org.bitcex;

import org.specs.Specification;
import net.liftweb.json.parse

class GoggleCurrencySpec extends Specification {

      "GoogleCurrency json extraction" should {

        val response = """{lhs: "1 Euro",rhs: "9.00730422 Swedish kronor",error: "",icc: true} """
        val json = parse(response)
  //      val googleCurrency = GoogleCurrency(json)


    }


}
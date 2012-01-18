package org.bitcex.model

import org.specs.Specification

class PasswordSpec extends Specification {
  "A Password " should {
    val clear = "passwd"
    val password = Password(clear)

    "hash the password" in {
      password.hashed must_!= clear
    }
    "add a salt" in {
      password.salt.size must_== Password.saltSize
    }
    "return true on compare with right password" in {
      password.equals(clear) mustBe true
    }
    "return false on compare with the wrong password" in {
      password.equals("wrong") mustBe false
    }
    "have a toHexString method" in {
      password.toHexString.isEmpty mustBe false
    }
  }
}
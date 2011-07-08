package org.bitcex.model

import org.specs.Specification

class InMemoryUserServiceSpec extends Specification {

  "InMemoryUserService" should {

    val dut = new InMemoryUserService()

    "have a create users" in {

      val user = dut.create("name1", "mail1", "password1", SEK(1), BTC(10))
      user.name must_== Name("name1")
      user.email must_== Email("mail1")
      user.password.equals("password1") mustBe true
      user.sek must_== SEK(1)
      user.btc must_== BTC(10)
    }

    "Throw execpetion if trying to create new user the the same email" in {
      dut.create("name1", "mail1", "password1", SEK(1), BTC(10))
      dut.create("name2", "mail1", "password2", SEK(2), BTC(20)) must throwAn[IllegalArgumentException]
    }

    "Find a user by email" in {
      val created = dut.create("name1", "mail1", "password1", SEK(1), BTC(10))
      val found = dut.findByEmail(Email("mail1"))
      found.isDefined mustBe true
      found.get mustEq created
    }

    "Return None for an unknown mail" in {
      val found = dut.findByEmail(Email("mail1"))
      found.isDefined mustBe false
    }

    "Find a user by id" in {
      val created = dut.create("name1", "mail1", "password1", SEK(1), BTC(10))
      val found = dut.findById(created.id)
      found.isDefined mustBe true
      found.get mustEq created
    }

    "Return None for an unknown id" in {
      val found = dut.findById(UserId("not found"))
      found.isDefined mustBe false
    }

    "Update an existing user" in {
      val created = dut.create("name1", "mail1", "password1", SEK(1), BTC(10))
      val changed = created.copy(email = Email("mail2"))
      val updated = dut.update(changed)
      updated mustEq changed
      dut.findByEmail(Email("mail1")) mustBe None
      dut.findByEmail(Email("mail2")).get mustBe updated
    }

    "Throw an exception when trying to update a non existing user" in {
      val created = dut.create("name1", "mail1", "password1", SEK(1), BTC(10))
      val changed = created.copy(name = Name("New Name"))
      val updated = dut.update(changed)
      updated mustEq changed
    }

    "Throw an excpetion when trying to update the email to another users email" in {
      val created = dut.create("name1", "mail1", "password1", SEK(1), BTC(10))
      dut.create("name2", "mail2", "password2")
      val changed = created.copy(email = Email("mail2"))
      dut.update(changed) must throwAn[IllegalArgumentException]
    }
  }

}

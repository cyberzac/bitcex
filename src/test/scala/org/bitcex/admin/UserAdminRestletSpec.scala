package org.bitcex.admin

import org.specs2.mutable.Specification
import org.specs2.mock._
import org.bitcex.userservice.UserService
import org.bitcex.model._
import akka.actor.TypedActor

class UserAdminRestletSpec extends Specification with Mockito {


  "The UserAdminRestlet" should {
    val name = Name("Martin Zachrison")
    val email = Email("mail@internet")
    val clear = "secret"
    val password = Password(clear)
    val userId = UserId("0")
    val user = User(name, email, userId, password, SEK(5), BTC(15))
    val userService = mock[UserService]
    userService.create(name, email, clear) returns user
    userService.findById(userId) returns Some(user)
    val dut = TypedActor.newInstance(classOf[UserAdmin], new UserAdminRestlet(userService))


    "create a new user " in {
      val createdUserId = dut.create("Martin Zachrison", "mail@internet", "secret")
      createdUserId must_==  userId
    }

    "Add SEK to a user" in {
      val newAmount = dut.addSek("0", 10.5)
      newAmount must_== SEK(15.5)
      there was one(userService).update(user.copy(sek = newAmount))
    }

    "Add BTC to a user" in {
      val newAmount = dut.addBtc("0", 10.5)
      newAmount must_== BTC(25.5)
      there was one(userService).update(user.copy(btc = newAmount))
    }
  }
}
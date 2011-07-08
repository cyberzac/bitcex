package org.bitcex.model

import org.specs.Specification
import akka.actor.Actor._

class OrderSpec extends Specification {

  "AskOrder" should {
    "Have a total" in {
      val user = User(Name("Nisse"), Email("mail"), UserId("1"), Password("pw"))
      val userRef = actorOf(new UserActor(user))
      AskOrderSEK(BTC(10), SEK(5), userRef).total must_== SEK(50)
    }
  }

}
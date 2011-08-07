package org.bitcex.model

import org.specs.Specification
import akka.actor.Actor._

class OrderSpec extends Specification {

  val user = User(Name("Nisse"), Email("mail"), UserId("1"), Password("pw"))
  val userRef = actorOf(new UserActor(user))
  val askOrderSEK = AskOrderSEK(BTC(10), SEK(5), userRef)

  "AskOrderSEK" should {
    "Have a total" in {
      askOrderSEK.total must_== SEK(50)
    }

    "Have a create method" in {
      val newOrder = askOrderSEK.create(BTC(4))
      newOrder.amount must_== BTC(4)
      newOrder.price must_== SEK(5)
      newOrder.sellerRef must_== userRef
    }
  }
}
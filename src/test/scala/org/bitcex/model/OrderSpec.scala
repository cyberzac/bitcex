package org.bitcex.model

import akka.actor.Actor._
import org.specs2.mutable.Specification


class OrderSpec extends Specification {

  val user = User("Nisse", "mail", "1", "pw")
  val userRef = actorOf(new UserActor(user))
  val askOrderSEK = AskOrderSEK(BTC(10), SEK(5), userRef)

  "AskOrderSEK" should {
    "Have a total method" in {
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
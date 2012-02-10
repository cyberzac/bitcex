package org.bitcex.model


import org.specs.Specification
import akka.testkit.TestActorRef
import akka.actor.Actor._
import org.specs.mock.Mockito
import org.bitcex.userservice.UserService
import org.bitcex.messages.GetUserMsg

class UserActorSpec extends Specification with Mockito {

  val userService = mock[UserService]
  val otherUserService = mock[UserService]

  "A UserActor" should {
    val user = User("Name", "name@mail.com", "1", "password", SEK(100), BTC(100))
    val otherUser = User("Other", "other@mail.com", "1", "password", SEK(100), BTC(100))
    val actorRef = TestActorRef(new UserActor(user, userService)).start()
    val otherRef = actorOf(new UserActor(otherUser, otherUserService))
    val actor = actorRef.underlyingActor

    "Update btc and sek values for a bid trade" in {
      val trade = Trade(BTC(5), SEK(10), sellerRef = otherRef, buyerRef = actorRef)
      actorRef ! trade
      val user = actor.user
      user.btc must_== BTC(105)
      user.sek must_== SEK(50)
      userService.update(user)
    }
    "Update btc and sek values for a ask trade" in {
      val trade = Trade(BTC(5), SEK(10), sellerRef = actorRef, buyerRef = otherRef)
      actorRef ! trade
      val user = actor.user
      user.btc must_== BTC(95)
      user.sek must_== SEK(150)
      userService.update(user)
    }

  }

}
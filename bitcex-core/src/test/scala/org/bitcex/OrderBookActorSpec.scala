/*
 * Copyright © 2012 Martin Zachrison.
 *
 *     This file is part of bitcex
 *
 *     bitcex is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     bitcex is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Pu1blic License
 *     along with bitcex.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.bitcex

import messages.{Orders, ListOrders}
import model._
import org.specs2.mutable.Specification
import akka.actor.Actor._
import akka.testkit.TestKit
import akka.util.Duration
import java.util.concurrent.TimeUnit
import org.specs2.execute.Success
import org.specs2.mock.Mockito
import userservice.UserService


class OrderBookActorSpec extends Specification with Mockito {

  val userService = mock[UserService]

  class Inner extends TestKit {
    val orderBook = actorOf[OrderBookActor[BTC, SEK]]
    orderBook.start()
    orderBook ! askOrderSEK_10_5
    orderBook ! askOrderSEK_10_6
    orderBook ! bidOrderSEK_9_4_bertil

    def listOrder = {
      within(Duration(100, TimeUnit.MILLISECONDS)) {
        orderBook ! ListOrders
        val orders = Orders[BTC, SEK](List(askOrderSEK_10_5, askOrderSEK_10_6), List(bidOrderSEK_9_4_bertil))
        expectMsg(orders)
      }
    }

    def listOrderUserFilter = {
      within(Duration(100, TimeUnit.MILLISECONDS)) {
        orderBook ! ListOrders(bertilRef)
        val orders = Orders(List(), List(bidOrderSEK_9_4_bertil))
        expectMsg(orders)
      }

    }
  }

  val user = User("Nisse", "mail", "1", "pw")
  val userRef = actorOf(new UserActor(user, userService))
  val askOrderSEK_10_5 = AskOrderSEK(BTC(10), SEK(5), userRef)
  val askOrderSEK_10_6 = AskOrderSEK(BTC(10), SEK(6), userRef)
  val bertil = User("Bertil", "bmail", "2", "pw")
  val bertilRef = actorOf(new UserActor(bertil, userService))
  val bidOrderSEK_9_4_bertil = BidOrderSEK(BTC(9), SEK(4), bertilRef)

  "An OrderBookActor" should {
    "Return all ask orders for ListOrder" in {
      val inner = new Inner
      inner.listOrder
      Success()
    }
    "Return an filter list for ListOrder(userRef)" in {
      val inner = new Inner
      inner.listOrderUserFilter
      Success()
    }

  }

}
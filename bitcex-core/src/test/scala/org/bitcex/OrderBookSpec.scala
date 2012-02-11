package org.bitcex

import model._
import org.specs.Specification
import akka.actor.Actor._
import akka.actor.ActorRef
import org.specs.mock.Mockito
import userservice.UserService

class OrderBookSpec extends Specification with Mockito {

  val userService = mock[UserService]

  val seller = User("Seller", "mail", "1", "pw")
  val sellerRef = actorOf(new UserActor(buyer, userService))

  val buyer = User("Buyer", "mail", "2", "pw")
  val buyerRef = actorOf(new UserActor(buyer, userService))

  "An OrderBook" should {
    "Que an AskOrder if no match is possible" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(ask(5, 10)).isEmpty mustBe true
    }

    "Que AskOrders in rising order" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(ask(5, 10)).isEmpty mustBe true
      dut.matchOrder(ask(5, 9)).isEmpty mustBe true
      dut.matchOrder(ask(5, 11)).isEmpty mustBe true
      verifyOrders(dut.askOrders, ask(5, 9), ask(5, 10), ask(5, 11))
      dut.bidOrders.isEmpty mustBe true
    }

    "Que an BidOrder if no asks are present" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(bid(3, 10)).isEmpty mustBe true
      dut.askOrders.isEmpty mustBe true
      verifyOrders(dut.bidOrders, bid(3, 10))
    }

    "Que an BidOrder if no match is possible" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(ask(5, 11)).isEmpty mustBe true
      dut.matchOrder(bid(3, 10)).isEmpty mustBe true
      verifyOrders(dut.askOrders, ask(5, 11))
      verifyOrders(dut.bidOrders, bid(3, 10))
    }

    "Que BidOrders in falling order" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(bid(3, 10)).isEmpty mustBe true
      dut.matchOrder(bid(5, 9)).isEmpty mustBe true
      dut.matchOrder(bid(5, 11)).isEmpty mustBe true
      verifyOrders(dut.bidOrders, bid(5, 11), bid(3, 10), bid(5, 9))
      dut.askOrders.isEmpty mustBe true
    }

    "Return a trade if ask and bid are equal for an bid order" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(ask(5, 9)).isEmpty mustBe true
      val trade = dut.matchOrder(bid(5, 9))(0)
      trade.amount must_== BTC(5)
      trade.price must_== SEK(9)
      trade.sellerRef mustBe sellerRef
      trade.buyerRef mustBe buyerRef
    }

    "Remove orders in a matched trade for an bid order" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(ask(5, 9)).isEmpty mustBe true
      dut.matchOrder(bid(5, 9))(0)
      dut.askOrders.isEmpty mustBe true
      dut.bidOrders.isEmpty mustBe true
    }

    "Return a trade with the oldest price if the ask and bid overrun each other for a bid order" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(ask(5, 9)).isEmpty mustBe true
      val trade = dut.matchOrder(bid(5, 11))(0)
      trade.amount must_== BTC(5)
      trade.price must_== SEK(9)
      trade.sellerRef mustBe sellerRef
      trade.buyerRef mustBe buyerRef
    }

    "Return a partial trade if the ask and bid amounts differ" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(ask(5, 9)).isEmpty mustBe true
      val trade = dut.matchOrder(bid(3, 10))(0)
      trade.amount must_== BTC(3)
      trade.price must_== SEK(9)
      trade.sellerRef mustBe sellerRef
      trade.buyerRef mustBe buyerRef
    }

    "Replace the ask order with an ask order with the remaing amount if the match bid amount was lower" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(ask(5, 9)).isEmpty mustBe true
      dut.matchOrder(bid(3, 10))
      verifyOrders(dut.askOrders, ask(2, 9))
    }

    "Replace the bid order with an bid order with the remaing amount if the match ask amount was lower" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(bid(9, 10)).isEmpty mustBe true
      dut.matchOrder(ask(5, 9))
      verifyOrders(dut.bidOrders, bid(4, 10))
    }

    "Match multiple asks and yield several Trades for one bid order" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(ask(5, 9)).isEmpty mustBe true
      dut.matchOrder(ask(5, 10)).isEmpty mustBe true
      val trades = dut.matchOrder(bid(11, 10))
      val trade1 = Trade(BTC(5), SEK(9), sellerRef, buyerRef)
      val trade2 = Trade(BTC(5), SEK(10), sellerRef, buyerRef)
      trades must containInOrder(trade1, trade2)
      dut.askOrders.isEmpty mustBe true
      verifyOrders(dut.bidOrders, bid(1, 10))
    }

    "Match multiple bids and yield several Trades for one ask order" in {
      val dut = new OrderBook[BTC, SEK]()
      dut.matchOrder(bid(3, 9)).isEmpty mustBe true
      dut.matchOrder(bid(3, 10)).isEmpty mustBe true
      dut.matchOrder(bid(3, 8)).isEmpty mustBe true
      dut.matchOrder((bid(2, 9))).isEmpty mustBe true
      val trades = dut.matchOrder(ask(10, 9))
      val trade1 = Trade(BTC(3), SEK(9), sellerRef, buyerRef)
      val trade2 = Trade(BTC(3), SEK(10), sellerRef, buyerRef)
      val trade3 = Trade(BTC(2), SEK(9), sellerRef, buyerRef)
      trades must containInOrder(trade1, trade2, trade3)
      verifyOrders(dut.bidOrders, bid(3, 8))
      verifyOrders(dut.askOrders, ask(2, 9))
    }
  }

  def verifyOrders(orders: List[Order[BTC, SEK]], expected: Order[BTC, SEK]*) {
    val zip = orders zip expected
    for ((actual, expected) <- zip) {
      actual.amount must_== expected.amount
      actual.price must_== expected.price
    }
  }

  implicit def int2Btc(i: Int): BTC = BTC(i)

  implicit def int2Sek(i: Int): SEK = SEK(i)

  def bid(amount: BTC, price: SEK, buyerRef: ActorRef = buyerRef) = BidOrderSEK(amount, price, buyerRef)

  def ask(amount: BTC, price: SEK, sellerRef: ActorRef = sellerRef) = AskOrderSEK(amount, price, sellerRef)
}
package org.bitcex

import model._
import org.specs.Specification
import akka.actor.Actor._
import akka.actor.ActorRef

class MatcherSpec extends Specification {

  val seller = User(Name("Seller"), Email("mail"), UserId("1"), Password("pw"))
  val sellerRef = actorOf(new UserActor(buyer))
  val ask_5_10 = AskOrderSEK(BTC(5), SEK(10), sellerRef)
  val ask_5_9 = AskOrderSEK(BTC(5), SEK(9), sellerRef)
  val ask_2_9 = AskOrderSEK(BTC(2), SEK(9), sellerRef)
  val ask_5_11 = AskOrderSEK(BTC(5), SEK(11), sellerRef)

  val buyer = User(Name("Seller"), Email("mail"), UserId("1"), Password("pw"))
  val buyerRef = actorOf(new UserActor(buyer))
  val bid_3_10 = BidOrderSEK(BTC(3), SEK(10), buyerRef)
  val bid_9_10 = BidOrderSEK(BTC(9), SEK(10), buyerRef)
  val bid_5_9 = BidOrderSEK(BTC(5), SEK(9), buyerRef)
  val bid_5_11 = BidOrderSEK(BTC(5), SEK(11), buyerRef)
  val bid_11_10 = BidOrderSEK(BTC(11), SEK(10), buyerRef)
  val bid_1_10 = BidOrderSEK(BTC(1), SEK(10), buyerRef)

  "A Matcher" should {
    "Que an AskOrder if no match is possible" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(ask_5_10).isEmpty mustBe true
    }

    "Que AskOrders in rising order" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(ask_5_10).isEmpty mustBe true
      dut.matchOrder(ask_5_9).isEmpty mustBe true
      dut.matchOrder(ask_5_11).isEmpty mustBe true
      dut.askOrders must containInOrder(ask_5_9, ask_5_10, ask_5_11)
      dut.bidOrders must haveSize(0)
    }

    "Que an BidOrder if no match is possible" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(bid_3_10).isEmpty mustBe true
    }

    "Que BidOrders in falling order" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(bid_3_10).isEmpty mustBe true
      dut.matchOrder(bid_5_9).isEmpty mustBe true
      dut.matchOrder(bid_5_11).isEmpty mustBe true
      dut.bidOrders must containInOrder(bid_5_11, bid_3_10, bid_5_9)
      dut.askOrders must haveSize(0)
    }

    "Return a trade if ask and bid are equal for an bid order" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(ask_5_9).isEmpty mustBe true
      val trade = dut.matchOrder(bid_5_9)(0)
      trade.amount must_== BTC(5)
      trade.price must_== SEK(9)
      trade.sellerRef mustBe sellerRef
      trade.buyerRef mustBe buyerRef
    }

    "Remove orders in a matched trade for an bid order" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(ask_5_9).isEmpty mustBe true
      dut.matchOrder(bid_5_9)(0)
      dut.askOrders must haveSize(0)
      dut.bidOrders must haveSize(0)
    }

    "Return a trade with average price if the ask and bid overrun each other for a bid order" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(ask_5_9).isEmpty mustBe true
      val trade = dut.matchOrder(bid_5_11)(0)
      trade.amount must_== BTC(5)
      trade.price must_== SEK(10)
      trade.sellerRef mustBe sellerRef
      trade.buyerRef mustBe buyerRef
    }

    "Return a partial trade if the ask and bid amounts differ" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(ask_5_9).isEmpty mustBe true
      val trade = dut.matchOrder(bid_3_10)(0)
      trade.amount must_== BTC(3)
      trade.price must_== SEK(9.5)
      trade.sellerRef mustBe sellerRef
      trade.buyerRef mustBe buyerRef
    }

    "Replace the ask order with an ask order with the remaing amount if the match bid amount was lower" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(ask_5_9).isEmpty mustBe true
      dut.matchOrder(bid_3_10)(0)
      dut.askOrders must containInOrder(ask_2_9)
    }

    "Match multiple asks and yield several Trades for one bid order" in {
      val dut = new Matcher[BTC, SEK]()
      dut.matchOrder(ask_5_9).isEmpty mustBe true
      dut.matchOrder(ask_5_10).isEmpty mustBe true
      val trades = dut.matchOrder(bid_11_10)
      val trade1 = Trade(BTC(5), SEK(10), sellerRef, buyerRef)
      val trade2 = Trade(BTC(5), SEK(9.5),sellerRef, buyerRef)
      trades must containInOrder(trade1, trade2)
      dut.askOrders must haveSize(0)
      dut.bidOrders must  containInOrder(bid_1_10)

    }

    //Todo match ask for existing bids

  }

  def bidOrder(amount:BTC, price:SEK, buyerRef:ActorRef = buyerRef) = BidOrderSEK(amount, price,buyerRef)
  def askOrder(amount:BTC, price:SEK, sellerRef:ActorRef = sellerRef) = AskOrderSEK(amount, price, sellerRef)
}
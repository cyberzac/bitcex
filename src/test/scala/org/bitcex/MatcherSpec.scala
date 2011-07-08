package org.bitcex

import model._
import org.specs.Specification
import akka.actor.Actor._

class MatcherSpec extends Specification {

  val seller = User(Name("Seller"), Email("mail"), UserId("1"), Password("pw"))
  val sellerRef = actorOf(new UserActor(buyer))
  val ask_5_10 = AskOrderSEK(BTC(5), SEK(10), sellerRef)
  val ask_5_9 = AskOrderSEK(BTC(5), SEK(9), sellerRef)
  val ask_5_11 = AskOrderSEK(BTC(5), SEK(11), sellerRef)

  val buyer = User(Name("Seller"), Email("mail"), UserId("1"), Password("pw"))
  val buyerRef = actorOf(new UserActor(buyer))
  val bid_3_10 = BidOrderSEK(BTC(5), SEK(10), buyerRef)
  val bid_5_9 = BidOrderSEK(BTC(5), SEK(9), buyerRef)
  val bid_5_11 = BidOrderSEK(BTC(5), SEK(11), buyerRef)

  "A Matcher" should {
    "Que an AskOrder if no match is possible" in {
      val dut = new Matcher()
      dut.matchOrder(ask_5_10) mustBe None
    }

    "Que AskOrders in rising order" in {
      val dut = new Matcher()
      dut.matchOrder(ask_5_10) mustBe None
      dut.matchOrder(ask_5_9) mustBe None
      dut.matchOrder(ask_5_11) mustBe None
      dut.askOrders must containInOrder(ask_5_9, ask_5_10, ask_5_11)
      dut.bidOrders must haveSize(0)
    }

    "Que an BidOrder if no match is possible" in {
      val dut = new Matcher()
      dut.matchOrder(bid_3_10) mustBe None
    }

    "Que BidOrders in falling order" in {
      val dut = new Matcher()
      dut.matchOrder(bid_3_10) mustBe None
      dut.matchOrder(bid_5_9) mustBe None
      dut.matchOrder(bid_5_11) mustBe None
      dut.bidOrders must containInOrder(bid_5_11, bid_3_10, bid_5_9)
      dut.askOrders must haveSize(0)
    }

    "Return a match if ask and bid are equal" in {
      val dut = new Matcher()
        dut.matchOrder(ask_5_9) mustBe None
        val trade = dut.matchOrder(bid_5_9).get
        trade.amount must_== BTC(5)
        trade.price must_== SEK(9)
      //Todo compare the ask and bid orders
   //     trade.askOrder must_== ask_5_9
   //     trade.bidOrder must_== bid_5_9
    }
    // Todo remove filled orders

    //Todo match ask for existing bids
    // Todo Partial fill of orders
  }

}
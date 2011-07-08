package org.bitcex.model

import akka.actor.Actor

class UserActor(var user:User) extends Actor {

  def receive:Receive = {

    case bidOrder:BidOrderSEK => {

    }

    case trade@Trade(amount, price, askOrder, bidOrder) =>  {

    }

  }



}
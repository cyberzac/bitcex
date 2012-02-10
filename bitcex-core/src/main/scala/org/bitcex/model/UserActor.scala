package org.bitcex.model

import akka.actor.Actor
import org.bitcex.messages.GetUserMsg
import com.weiglewilczek.slf4s.Logging
import org.bitcex.userservice.UserService

class UserActor(var user: User, val userService:UserService) extends Actor with Logging {


  def receive: Receive = {

    case trade@Trade(amount: BTC, price: SEK, seller, `self`) => {
      user = user.copy(btc = user.btc + amount).copy(sek = user.sek - (price * amount.amount))
      userService.update(user);
      logger.info(user.name + " bought " + amount + " at " + price + "/BTC")
    }

    case trade@Trade(amount: BTC, price: SEK, `self`, buyer) => {
      user = user.copy(btc = user.btc - amount).copy(sek = user.sek + (price * amount.amount))
      userService.update(user);
      logger.info(user.name + " sold " + amount + " at " + price + "/BTC")
    }

    case GetUserMsg => self reply user

  }


}
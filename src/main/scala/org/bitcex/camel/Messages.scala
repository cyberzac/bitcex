package org.bitcex.camel

import akka.actor.ActorRef

case class GetTicker(receiver: Option[ActorRef] = None)
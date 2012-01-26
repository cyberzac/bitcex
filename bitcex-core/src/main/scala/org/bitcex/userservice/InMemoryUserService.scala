package org.bitcex.userservice

import java.lang.IllegalArgumentException
import org.springframework.stereotype.Component
import org.bitcex.model._
import akka.actor.TypedActor
import org.slf4j.LoggerFactory

@Component
class InMemoryUserService extends TypedActor with UserService {
  val log = LoggerFactory.getLogger(getClass)
  var userId = 0
  var users = Map[UserId, User]()

  def remove(user: User): Boolean = false

  def update(updatedUser: User): User = {
    val userOption = users.get(updatedUser.id)
    val user = userOption.getOrElse(throw new IllegalArgumentException("User %s not found".format(updatedUser)))
    val updatedEmail = updatedUser.email
    if (user.email != updatedEmail && users.values.find(_.email == updatedUser.email).isDefined) throw new IllegalArgumentException("%s is already in use".format(updatedUser))
    users = users + (updatedUser.id -> updatedUser)
    log.debug("User {} updated to {}", updatedUser.id, updatedUser)
    updatedUser
  }

  def findByEmail(email: Email): Option[User] = {
    val user = users.values.find(_.email == email)
    log.debug("findByEmail: {}", if (user.isDefined) user else "No user with email %s".format(email))
    user
  }

  def findById(id: UserId): Option[User] = {
    val user = users.get(id)
    log.debug("findById: {}", if (user.isDefined) user else "No user with id %s".format(id))
    user
  }

  def create(name: Name, email: Email, clear: String, sek: SEK = SEK(0), btc: BTC = BTC(0)): User = {
    if (findByEmail(email).isDefined) throw new IllegalArgumentException("%s is already in use".format(email))
    userId += 1
    val user = User(name, email, userId.toString, Password(clear), sek, btc)
    users = users + (user.id -> user)
    log.info("Created user {}", user)
    user
  }

}
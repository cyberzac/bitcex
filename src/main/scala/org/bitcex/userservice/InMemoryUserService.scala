package org.bitcex.userservice

import java.lang.IllegalArgumentException
import org.springframework.stereotype.Component
import org.bitcex.model._
import akka.actor.TypedActor

@Component
class InMemoryUserService extends TypedActor with  UserService {
  var userId = 0
  var users = Map[UserId, User]()

  def remove(user: User): Boolean = false

  def update(updatedUser: User): User = {
    val userOption = users.get(updatedUser.id)
    val user = userOption.getOrElse(throw new IllegalArgumentException("User %s not found".format(updatedUser)))
    val updatedEmail = updatedUser.email
    if (user.email != updatedEmail && users.values.find(_.email == updatedUser.email).isDefined) throw new IllegalArgumentException("%s is already in use".format(updatedUser))
    users = users + (updatedUser.id -> updatedUser)
    updatedUser
  }

  def findByEmail(email: Email): Option[User] = users.values.find(_.email == email)

  def findById(id: UserId): Option[User] = users.get(id)

  def create(name: Name, email: Email, clear: String, sek: SEK = SEK(0), btc: BTC = BTC(0)): User = {
    if (findByEmail(email).isDefined) throw new IllegalArgumentException("%s is already in use".format(email))
    userId += 1
    val user = User(name,email, userId.toString, Password(clear), sek, btc)
    users = users + (user.id -> user)
    user
  }

}
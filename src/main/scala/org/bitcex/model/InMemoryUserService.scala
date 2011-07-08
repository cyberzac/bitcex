package org.bitcex.model

import java.lang.IllegalArgumentException

class InMemoryUserService extends UserService {
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

  def create(name: String, email: String, password: String, sek: SEK, btc: BTC): User = {
    if (findByEmail(Email(email)).isDefined) throw new IllegalArgumentException("%s is already in use".format(email))
    userId += 1
    val user = User(Name(name),Email(email), UserId(userId.toString), Password(password), sek, btc)
    users = users + (user.id -> user)
    user
  }

}
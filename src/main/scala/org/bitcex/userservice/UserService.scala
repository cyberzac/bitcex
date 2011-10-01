package org.bitcex.userservice

import org.bitcex.model._

trait UserService {

  /**
   * Creates a new user
   */
  def create(name:Name, email:Email, clear:String, sek:SEK=SEK(0), btc:BTC=BTC(0)):User
  /**
   * Finds an existing user
   */
  def findById(userId:UserId):Option[User]

  /**
   * Finds by email
   */
  def findByEmail(email:Email):Option[User]

  /**
   * Updates a user
   */
  def update(updatedUser:User):User

  /**
   * Removes a user
   */
  def remove(user:User):Boolean

}


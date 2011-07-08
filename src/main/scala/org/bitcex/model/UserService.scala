package org.bitcex.model

trait UserService {

  /**
   * Creates a new user
   */
  def create(name:String, email:String, password:String, sek:SEK=SEK(0), btc:BTC=BTC(0)):User
  /**
   * Finds an existing user
   */
  def findById(id:UserId):Option[User]

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


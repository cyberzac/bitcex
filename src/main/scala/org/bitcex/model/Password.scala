package org.bitcex.model

import util.Random
import collection.mutable.WrappedArray

object Password {

  val saltSize = 20
  val hashAlgorithm = "SHA1"
  val random = new Random()

  def apply(clear: String): Password = {
    val salt = createSalt
    apply(salt, clear)
  }

  def apply(salt: Array[Byte], clear: String): Password = {
    new Password(salt, hash(salt, clear))
  }

  def createSalt: Array[Byte] = {
    var bytes = new Array[Byte](saltSize)
    random.nextBytes(bytes)
    bytes
  }

  def hash(salt: Array[Byte], value: String): Array[Byte] = {
    import java.security.MessageDigest
    val digester = MessageDigest.getInstance(hashAlgorithm)
    digester.update(salt)
    digester.digest(value.getBytes)
  }

  def toHexString(bytes:Array[Byte]):String = bytes.map("%02X" format _).mkString
}

case class Password(salt: Array[Byte], hashed: Array[Byte]) {

  def toHexString = Password.toHexString(salt ++ hashed)

  def equals(password: String): Boolean = {
    val otherHash = Password.hash(salt, password)
    ((otherHash): WrappedArray[Byte]) == ((hashed): WrappedArray[Byte])
  }
}
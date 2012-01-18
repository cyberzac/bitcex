/*
 * Copyright © 2011 Martin Zachrison.
 *
 *     This file is part of bitcex
 *
 *     bitcex is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     bitcex is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with bitcex.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.bitcex.admin

import org.bitcex.model.{User, SEK, BTC, UserId}
import akka.camel.consume
import org.apache.camel.Header

trait UserAdmin {

  @consume("restlet:/admin/users/{id}?restletMethod=GET")
  def get(@Header("id") userId: String): Option[User]

  @consume("restlet:/admin/users/?restletMethod=POST")
  def create(@Header("name") name: String, @Header("email") email: String, @Header("password") password: String): UserId

  def addBtc(id: String, amount: BigDecimal): BTC

  def addSek(id: String, amount: BigDecimal): SEK

}
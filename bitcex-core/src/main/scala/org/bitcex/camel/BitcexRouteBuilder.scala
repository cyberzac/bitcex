/*
 * Copyright © 2012 Martin Zachrison.
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

package org.bitcex.camel

import org.apache.camel.scala.dsl.builder.RouteBuilder


object BitcexRouteBuilder extends RouteBuilder {

  // Ecb currency rates
  val ecbUrl = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"
  //  ecbUrl.throttle(1 per 60*60*1000 ) --> "xslt:ecb.xsl" -->  "seda:ecbCurrency"

  // MtGox tickers
  val mtGoxTickerUrl = "https://mtgox.com/code/data/ticker.php"
  // mtGoxTickerUrl.throttle(1 per 5 *60*1000) -->  "seda:mtGoxTicker"

  "servlet:///update" --> mtGoxTickerUrl --> "seda:mtGoxTicker"

  //  "restlet:/admin/users/{id}" --> "bean:userAdminRestlet"

  "restlet:/demo/{id}?restletMethod=POST" transform (simple("Request type : ${header.CamelHttpMethod} and ID : ${header.id},  name ${header.name}"))
}
package org.bitcex.camel

import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder}
import org.springframework.stereotype.Component

@Component
class MyRouteBuilder extends  ScalaRouteBuilder {
  "servlet:///buy" -->  "velocity:buy.vm" --> "smtps://smtp.gmail.com?username=martin.zachrison&password=knt72/Qk&to=zac@cyberzac.se"
}
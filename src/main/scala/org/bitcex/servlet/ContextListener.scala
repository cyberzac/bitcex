package org.bitcex.servlet

import javax.servlet.{ServletContextEvent, ServletContextListener}
import akka.camel.CamelServiceManager._
import akka.camel.CamelContextManager
import org.bitcex.camel.{ActorStarter, BitcexRouteBuilder}
import org.slf4j.LoggerFactory
import org.apache.camel.component.restlet.RestletComponent
import org.restlet.Component


/**
 * Date: 2011-11-25
 * @author Martin Zachrison, Cygni AB <martin.zachrison@cygni.se>
 */
class ContextListener extends ServletContextListener {

  private val log = LoggerFactory.getLogger(getClass)

  def contextInitialized(sce: ServletContextEvent) {
    log.info("Starting Camel context")

    val restletComponent = new Component()
    val restletService = new RestletComponent(restletComponent)

    startCamelService
    CamelContextManager.init()
    val context = CamelContextManager.context.get
    context.addRoutes(BitcexRouteBuilder)
    CamelContextManager start


    val actorStarter = new ActorStarter()
  }

  def contextDestroyed(sce: ServletContextEvent) {
    log.info("Stopping Camel context")
    stopCamelService
  }
}
//name := "bitcex-core"

organization := "org.bitcex"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.9.1"

retrieveManaged := true

resolvers ++= Seq(
  "snapshots" at "http://scala-tools.org/repo-snapshots",
  "releases" at "http://scala-tools.org/repo-releases",
  "typesafe" at "http://repo.typesafe.com/typesafe/releases",
  "restlet" at "http://maven.restlet.org"
)

libraryDependencies ++= {
  val akka_version = "1.2"
  Seq(
    "se.scalablesolutions.akka" % "akka-actor" % akka_version,
    "se.scalablesolutions.akka" % "akka-spring" % akka_version,
    "se.scalablesolutions.akka" % "akka-camel" % akka_version,
    "se.scalablesolutions.akka" % "akka-camel-typed" % akka_version,
    "se.scalablesolutions.akka" % "akka-testkit" % akka_version
  )
}


// Todo check versions of unfiltered
// Todo check versions of dispatch

libraryDependencies ++= {
  val camelVersion = "2.8.1"
  val springVersion = "3.0.5.RELEASE"
  val slf4jVersion = "1.6.2"
  val liftVersion = "2.4-SNAPSHOT"
  val restletVersion = "2.0.8"
  val logbackVersion = "0.9.30"
  //  val unfiltered_version = "0.4.1"
  val dispatch_version = "0.8.5"
  Seq(
    "joda-time" % "joda-time" % "2.0",
    "org.joda" % "joda-convert" % "1.2",
    "net.liftweb" %% "lift-json" % liftVersion,
    "saxon" % "saxon-dom" % "9.1.0.8j",
    "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7" % "test",
    "org.specs2" %% "specs2" % "1.5",
    //    "org.specs2" %% "specs2-scalaz-core" % "6.0.RC2" % "test",
    "org.scalatest" % "scalatest" % "1.2" % "test",
    //  "org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "jetty",
    //   "net.databinder" %% "unfiltered-jetty" % unfiltered_version,
    //   "net.databinder" %% "unfiltered-filter" % unfiltered_version,
    "net.databinder" %% "dispatch-http" % dispatch_version,
    //     "org.apache.camel" % "apache-camel" % camelVersion,
    "org.apache.camel" % "camel-core" % camelVersion,
    "org.apache.camel" % "camel-scala" % camelVersion,
    "org.apache.camel" % "camel-servlet" % camelVersion,
    "org.apache.camel" % "camel-spring" % camelVersion,
    "org.apache.camel" % "camel-velocity" % camelVersion,
    "org.apache.camel" % "camel-jetty" % camelVersion,
    "org.apache.camel" % "camel-mail" % camelVersion,
    "org.apache.camel" % "camel-restlet" % camelVersion,
    "org.apache.camel" % "camel-test" % camelVersion % "test",
    "org.springframework" % "spring-core" % springVersion,
    "org.springframework" % "spring-web" % springVersion,
    "org.restlet.jee" % "org.restlet.ext.spring" % restletVersion,
    "org.restlet.jee" % "org.restlet.ext.slf4j" % restletVersion,
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    // "org.slf4j" % "slf4j-log4j12" % slf4jVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.weiglewilczek.slf4s" %% "slf4s" % "1.0.7"
  )
}





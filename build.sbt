
organization := "org.bitcex"

name := "Svenska Bitcoin"

version := "1.0-SNAPSHOT"

scalaVersion := "2.9.0-1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases"


libraryDependencies ++= Seq(
    "se.scalablesolutions.akka" % "akka-actor" % "1.1.2",
    "se.scalablesolutions.akka" % "akka-spring" % "1.1.2",
    "se.scalablesolutions.akka" % "akka-camel" % "1.1.2",
    "se.scalablesolutions.akka" % "akka-camel-typed" % "1.1.2",
    "joda-time" % "joda-time" % "1.6.2",
    "net.liftweb" %% "lift-json" % "2.4-SNAPSHOT",
      // "net.debasishg" % "sjson_2.9.0" % "0.12",
    //  "net.sf.saxon" % "saxonhe" % "9.2.0_3j",
    //  "net.sourceforge.saxon" % "saxon" % "9.1.0.8",
    "saxon" % "saxon" % "9.1.0.8j",
    "saxon" % "saxon-dom" % "9.1.0.8j",
    "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7" % "test",
    /*
     "org.specs2" %% "specs2" % "1.4" % "test",
     // with Scala 2.9.0
     "org.specs2" %% "specs2-scalaz-core" % "6.0.RC2" % "test",
     def specs2Framework = new TestFramework("org.specs2.runner.SpecsFramework")
     override def testFrameworks = super.testFrameworks ++ Seq(specs2Framework)
     "snapshots" at "http://scala-tools.org/repo-snapshots"
     "releases" at "http://scala-tools.org/repo-releases"
     */
    "org.scalatest" % "scalatest" % "1.2" % "test",
    "org.eclipse.jetty" % "jetty-webapp" % "7.0.2.RC0" % "test",
    "org.apache.camel" % "camel-core" % "2.7.1",
    "org.apache.camel" % "camel-scala" % "2.7.1",
    "org.apache.camel" % "camel-servlet" % "2.7.1",
    "org.apache.camel" % "camel-spring" % "2.7.1",
    "org.apache.camel" % "camel-velocity" % "2.7.1",
    "org.apache.camel" % "camel-jetty" % "2.7.1",
    "org.apache.camel" % "camel-mail" % "2.7.1",
    //   "org.apache.camel" % "camel-saxon" % "2.7.1"
    "org.apache.camel" % "camel-test" % "2.7.1" % "test",
    "org.springframework" % "spring-core" % "3.0.5.RELEASE",
    "org.springframework" % "spring-web" % "3.0.5.RELEASE",
    "org.slf4j" % "slf4j-api" % "1.6.1",
    "org.slf4j" % "slf4j-log4j12" % "1.6.1"
)

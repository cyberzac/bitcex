
organization := "org.bitcex"

name := "Svenska Bitcoin"

version := "1.0-SNAPSHOT"

scalaVersion := "2.9.0-1"

retrieveManaged := true

seq(webSettings :_*)

  resolvers ++= Seq(
    "snapshots" at "http://scala-tools.org/repo-snapshots",
     "releases" at "http://scala-tools.org/repo-releases",
     "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases"
       )

libraryDependencies ++=  {
  val akka_version = "1.1.2"
  Seq(
    "se.scalablesolutions.akka" % "akka-actor" % akka_version,
    "se.scalablesolutions.akka" % "akka-spring" % akka_version,
    "se.scalablesolutions.akka" % "akka-camel" % akka_version,
    "se.scalablesolutions.akka" % "akka-camel-typed" % akka_version,
    "se.scalablesolutions.akka" % "akka-testkit" % akka_version
)
}

libraryDependencies ++=  Seq(
    "joda-time" % "joda-time" % "1.6.2",
    "net.liftweb" %% "lift-json" % "2.4-M1",
    "saxon" % "saxon-dom" % "9.1.0.8j",
    "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7" % "test",
    "org.specs2" %% "specs2" % "1.5",
    // with Scala 2.8.1
    /*
     "org.specs2" %% "specs2" % "1.4" % "test",
     // with Scala 2.9.0
     */
     "org.specs2" %% "specs2-scalaz-core" % "6.0.RC2" % "test",
/*
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
    "org.apache.camel" % "camel-test" % "2.7.1" % "test",
    "org.springframework" % "spring-core" % "3.0.5.RELEASE",
    "org.springframework" % "spring-web" % "3.0.5.RELEASE",
    "org.slf4j" % "slf4j-api" % "1.6.1",
    "org.slf4j" % "slf4j-log4j12" % "1.6.1",
    "com.weiglewilczek.slf4s" %% "slf4s" % "1.0.6"
)





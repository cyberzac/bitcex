import sbt._

class BitcexProject(info: ProjectInfo) extends DefaultWebProject(info) with AkkaProject {

  val akka_slf4j = akkaModule("slf4j")
  val akka_camel = akkaModule("camel")
  val akka_camel_typed = akkaModule("camel-typed")

  val java2Repo = "Java 2 repo" at "http://download.java.net/maven/2/"
  val saxonRepo = "Saxon @ Eviware" at "http://www.eviware.com/repository/maven2/"
  //val jbossRepo = "JBoss Repository" at "http://repository.jboss.org/maven2/"
  val scalaSnapshotsRepo = "Scala Snapshots" at "http://main.scala-tools.org/repo-snapshots"

  val joda_time = "joda-time" % "joda-time" % "1.6.2"

  val lift_json = "net.liftweb" %% "lift-json" % "2.4-SNAPSHOT"
  // val sjson ="net.debasishg" % "sjson_2.9.0" % "0.12"

//  val saxon = "net.sf.saxon" % "saxonhe" % "9.2.0_3j"
//  val saxon = "net.sourceforge.saxon" % "saxon" % "9.1.0.8"

  val saxon_version = "9.1.0.8j"
  val saxon =  "saxon" % "saxon" % saxon_version
  val saxon_dom = "saxon" % "saxon-dom" % saxon_version

  val specs = "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7" % "test"
  val scalaTest = "org.scalatest" % "scalatest" % "1.2" % "test"
  val jetty7 = "org.eclipse.jetty" % "jetty-webapp" % "7.0.2.RC0" % "test"

  val camel_version = "2.7.1"
  val camel_core = "org.apache.camel" % "camel-core" % camel_version withSources ()
  val camel_scala = "org.apache.camel" % "camel-scala" % camel_version withSources ()
  val camel_servlet = "org.apache.camel" % "camel-servlet" % camel_version
  val camel_spring = "org.apache.camel" % "camel-spring" % camel_version withSources ()
  val camel_velocity = "org.apache.camel" % "camel-velocity" % camel_version
  val camel_jetty = "org.apache.camel" % "camel-jetty" % camel_version
  val camel_mail = "org.apache.camel" % "camel-mail" % camel_version
//   val camel_saxon = "org.apache.camel" % "camel-saxon" % camel_version

  val camel_test = "org.apache.camel" % "camel-test" % camel_version % "test"

  val spring_version = "3.0.5.RELEASE"
  val spring_core = "org.springframework" % "spring-core" % spring_version
  val spring_web = "org.springframework" % "spring-web" % spring_version

  val slf4j_version = "1.6.1"
  val slf4j_api = "org.slf4j" % "slf4j-api" % slf4j_version
  val slf4j_log4j12 = "org.slf4j" % "slf4j-log4j12" % slf4j_version
}


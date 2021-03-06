name := "bitcex"

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




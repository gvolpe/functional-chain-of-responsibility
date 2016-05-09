name := """functional-chain-of-responsibility"""

version := "1.0"

scalaVersion := "2.11.7"

val scalazVersion = "7.1.3"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz" %% "scalaz-concurrent" % scalazVersion,
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

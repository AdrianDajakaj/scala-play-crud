// name := """scala-play-crud"""
// organization := "Jagiellonian University"

// version := "1.0-SNAPSHOT"

// lazy val root = (project in file(".")).enablePlugins(PlayScala)

// scalaVersion := "3.6.4"

// libraryDependencies += guice
// libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test

name := "scala-play-crud"
organization := "Jagiellonian University"
version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.6.4"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
  "com.typesafe.play" %% "filters-helpers" % "2.9.0"
)

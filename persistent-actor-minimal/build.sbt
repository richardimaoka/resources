import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "persistent-actor-minimal",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-persistence" % "2.5.9",
      scalaTest % Test
    )
  )

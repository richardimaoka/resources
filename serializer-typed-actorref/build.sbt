import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "serializer-typed-actorref",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.5.9",
      //"com.typesafe.akka" %% "akka-actor-typed" % "2.5.9",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.5-SNAPSHOT",
      scalaTest % Test
    )
  )

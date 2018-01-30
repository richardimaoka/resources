import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "kamon-playground",
    libraryDependencies ++= Seq(
      "io.kamon" %% "kamon-core" % "0.6.7",
      scalaTest % Test
    )
  )

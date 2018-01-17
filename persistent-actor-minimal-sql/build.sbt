import Dependencies._

/**
 * https://github.com/okumin/akka-persistence-sql-async
 */
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "persistent-actor-minimal-sql",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-persistence" % "2.5.6",
      "com.okumin" %% "akka-persistence-sql-async" % "0.5.1",
      "com.github.mauricio" %% "mysql-async" % "0.2.21",
      // "com.github.mauricio" %% "postgresql-async" % "0.2.20", // for postgres, but this example is for mysql, so not needed
      scalaTest % Test
    )
  )

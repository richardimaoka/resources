lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.12.4"
    )),
    name := "akka-http-stream-integration-failed",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"   % "10.1.3",
      "com.typesafe.akka" %% "akka-stream" % "2.5.12"
    )
  )

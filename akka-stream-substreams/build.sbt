lazy val akkaVersion = "2.5.12"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.12.4"
    )),
    name := "akka-stream-substreams",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-protobuf" % akkaVersion,
      "com.typesafe.akka" %% "akka-actor"  % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion
    )
  )

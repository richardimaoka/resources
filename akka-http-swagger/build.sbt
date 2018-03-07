lazy val akkaHttpVersion = "10.0.11"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.12.4"
    )),
    name := "akka-http-swagger",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.13.0"
    )
  )

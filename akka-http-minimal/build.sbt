lazy val akkaHttpVersion = "10.0.11"
lazy val akkaVersion    = "2.5.9"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.12.4"
    )),
    name := "akka-http-sample",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.0",
      "io.gatling"            % "gatling-test-framework"    % "2.3.0"
    )
  ).enablePlugins(GatlingPlugin)

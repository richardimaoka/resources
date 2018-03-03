lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.12.4"
    )),
    name := "akka-http-sample-gatling",
    libraryDependencies ++= Seq(
      "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.0",
      "io.gatling"            % "gatling-test-framework"    % "2.3.0"
    )
  ).enablePlugins(GatlingPlugin)

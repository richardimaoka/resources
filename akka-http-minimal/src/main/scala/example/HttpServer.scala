package example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object HttpServer extends {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("HttpServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    lazy val routes: Route =
      pathEndOrSingleSlash {
        complete("OK")
      }

    Http().bindAndHandle(routes, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
    Await.result(system.whenTerminated, Duration.Inf)
  }
}

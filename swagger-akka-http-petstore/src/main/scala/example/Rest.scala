package example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.RouteConcatenation
import akka.stream.ActorMaterializer
import example.endpoints.PetEndPoint

object Rest extends App with RouteConcatenation with PetEndPoint {
  implicit val system = ActorSystem("akka-http-sample")
  sys.addShutdownHook(system.terminate())

  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val routes =
    path(SwaggerDocService.basePath) {
        petRoute
    } ~ SwaggerDocService.routes

  Http().bindAndHandle(routes, "0.0.0.0", 9999)
}
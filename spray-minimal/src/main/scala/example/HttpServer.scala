package example

import akka.actor.{Actor, ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http
import spray.routing.HttpService

import scala.concurrent.duration._

// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {
  val myRoute =
    path("") {
      complete("Hello World")
    }
}

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


object HttpServer {
  def main(args: Array[String]): Unit = {
    // we need an ActorSystem to host our application in
    implicit val system = ActorSystem("on-spray-can")

    // create and start our service actor
    val service = system.actorOf(Props[MyServiceActor], "demo-service")

    implicit val timeout = Timeout(5.seconds)
    // start a new HTTP server on port 8090 with our service actor as the handler
    IO(Http) ? Http.Bind(service, interface = "localhost", port = 8090)
  }
}
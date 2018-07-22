package com.example.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.example.model.{Address, EnrichedUser, User}

object MainHighLevelAPI {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("MainHighLevelAPI")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    lazy val routes: Route =
      pathPrefix("users") {
        path("person1"){
          get {
            complete(User(name = "Joh Don", age = 150))
          }
        } ~
        path("person2"){
          get {
            complete(User(name = "Justin Bieber", age = 24))
          }
        } ~
        path("person3"){
          get {
            complete(User(name = "Peyton List", age = 20))
          }
        } ~
        path("person4") {
          get {
            complete(
              EnrichedUser(
                "Richard Imaoka",
                150,
                Address(
                  zip = 1112222,
                  street = "5-6-7 XYZ-street",
                  city = "New York",
                  state = "New York"
                )
              )
            )
          }
        }
      }
    Http().bindAndHandle(routes, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}

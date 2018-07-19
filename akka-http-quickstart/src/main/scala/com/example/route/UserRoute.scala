package com.example.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.model.User

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

object UserRoute {
  lazy val routes: Route =
    path("person1"){
      get {
        complete(User("Joh Don", 35))
      }
    } ~
    path("person2"){
      get {
        complete(User("Justin Bieber", 24))
      }
    } ~
    path("person3"){
      get {
        complete(User("Peyton List", 20))
      }
    }
}

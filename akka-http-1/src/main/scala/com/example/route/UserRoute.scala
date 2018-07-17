package com.example.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.model.User

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

object UserRoute {
  lazy val routes: Route = get {
    complete(User("Joh Don", 35))
  }
}

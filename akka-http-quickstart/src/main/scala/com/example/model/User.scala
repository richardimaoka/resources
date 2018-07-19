package com.example.model

import spray.json.DefaultJsonProtocol._ //for jsonFormat2
import spray.json.RootJsonFormat

case class User(name: String, age: Int)

object User {
  // formats for unmarshalling and marshalling
  implicit val userJsonFormat: RootJsonFormat[User] = jsonFormat2(User.apply)
}
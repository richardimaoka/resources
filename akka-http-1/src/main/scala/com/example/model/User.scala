package com.example.model

import spray.json.DefaultJsonProtocol._

case class User(name: String, age: Int)

object User {
  // formats for unmarshalling and marshalling
  implicit val userJsonFormat = jsonFormat2(User.apply)
}
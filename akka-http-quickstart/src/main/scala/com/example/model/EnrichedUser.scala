package com.example.model

import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat
import Address._ // implicit val addressJsonFormat

case class EnrichedUser(
  name:    String,
  age:     Int,
  address: Address //nested case class
)

object EnrichedUser {
  // this will fit in the implicit resolution,
  // enabling JSON/case class conversion
  implicit val enrichedUserJsonFormat: RootJsonFormat[EnrichedUser] = jsonFormat3(EnrichedUser.apply)
}
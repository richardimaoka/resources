package com.example.model

import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

case class Address(
  zip:    Int,
  street: String,
  city:   String,
  state:  String,
)

object Address {
  // this will fit in the implicit resolution,
  // enabling JSON/case class conversion
  implicit val addressJsonFormat: RootJsonFormat[Address] = jsonFormat4(Address.apply)
}
package com.example

import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

case class DataChunk(id: Int, data: String)

object DataChunk {
  implicit val dataChunkJsonFormat: RootJsonFormat[DataChunk]
    = jsonFormat2(DataChunk.apply)
}
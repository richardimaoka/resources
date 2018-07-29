package com.example

import akka.NotUsed
import akka.stream.scaladsl.Source
import scala.concurrent.duration._

object DataSource {
  def source: Source[DataChunk, NotUsed] =
    Source(List(
      DataChunk(1, "the first"),
      DataChunk(2, "the second"),
      DataChunk(3, "the thrid"),
      DataChunk(4, "the fourth"),
      DataChunk(5, "the fifth"),
      DataChunk(6, "the sixth"))
      // you need throttling for demonstration, otherwise
      // it's too fast and you don't see what's happening
    ).throttle(1, 1.second)
}

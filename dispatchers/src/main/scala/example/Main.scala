package example

import java.io.{PrintWriter, StringWriter}

import akka.actor.ActorSystem

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("exampleSystem")
    try {
      println(system.dispatchers)

    } catch {
      case t: Throwable => {
        val sw = new StringWriter
        t.printStackTrace(new PrintWriter(sw))
        println(t.getMessage)
        println(sw)
      }
    } finally {
      system.terminate()
    }
  }
}

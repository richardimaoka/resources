package example

import java.io.{PrintWriter, StringWriter}

import akka.actor.{Actor, ActorSystem, Props}

class MyDummyClass


import akka.dispatch.RequiresMessageQueue

class MyActor extends Actor
  with RequiresMessageQueue[MyDummyClass] {
  def receive = Actor.emptyBehavior
}

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("exampleSystem")
    try {
      val actor = system.actorOf(Props[MyActor])
      Thread.sleep(1000)
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

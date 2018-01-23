package example

import java.io.{PrintWriter, StringWriter}

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl._
import akka.actor.typed.scaladsl.adapter._
import akka.serialization.{SerializationExtension, SerializerWithStringManifest}
import com.typesafe.config.ConfigFactory

class EmptyActor extends akka.actor.Actor {
  def receive = akka.actor.Actor.emptyBehavior
}

object Main {
  def main(args: Array[String]): Unit = {

    val empty = Actor.immutable[String] { (_, msg) ⇒
      Actor.same
    }

    case class AAA(s: String, ref: ActorRef[String]) extends java.io.Serializable

    val system = akka.actor.ActorSystem("example", ConfigFactory.load()) //application.conf is empty
    try {
      val ref = system.spawn(empty, "empty")

      //Here ActorRefAdapter instance is used
      val original = AAA("hello", ref)
      val serialization = SerializationExtension(system)
      val serializer = serialization.findSerializerFor(original)

      println(s"Serializer for ${original.getClass} = " + serializer)

      val bytes = serializer.toBinary(original)
      val back = serialization.findSerializerFor(original) match {
        case s: SerializerWithStringManifest ⇒ s.fromBinary(bytes, s.manifest(original))
        case _ => serializer.fromBinary(bytes, manifest = None)
      }

      // Voilá!
      println(back, " ", back.getClass)
      println(original, " ", original.getClass)

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

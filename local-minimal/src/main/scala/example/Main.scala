package example

import java.io.{PrintWriter, StringWriter}

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class MessageReceiver extends Actor {
  def receive = {
    case s: String =>
      println(s"EchoActor: received message = $s")
  }
}

class MessageSender(targetReceiver: ActorRef) extends Actor {
  override def preStart(): Unit = {
    println(s"sending a message to $targetReceiver")
    targetReceiver ! "Hello!!"
  }

  def receive = Actor.emptyBehavior
}

object MessageSender {
  //pattern described in https://doc.akka.io/docs/akka/2.5/actors.html
  def props(targetReceiver: ActorRef) = Props(new MessageSender(targetReceiver))
}

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("exampleSystem")
    println(s"provider = ${system.settings.config.getString("akka.actor.provider")}")
    try {
      val receiver = system.actorOf(Props[MessageReceiver], "receiver")

      // Use object's props method to avoid a dangerous pattern described in
      // https://doc.akka.io/docs/akka/2.5/actors.html?language=scala#dangerous-variants
      system.actorOf(MessageSender.props(receiver), "sender")
      Thread.sleep(3000)
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

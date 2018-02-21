package example

import java.io.{PrintWriter, StringWriter}

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class MessageReceiver extends Actor {
  def receive = {
    case s: String =>
      println(s"${Thread.currentThread()}|[${self.path}]|EchoActor: received message = $s")
  }
}

class MessageSender(messageReceiver: ActorRef) extends Actor {
  override def preStart(): Unit = {
    val messages = List("Hello World", "Hello Universe", "Hello Galaxy")
    for(msg <- messages) {
      println(s"${Thread.currentThread()}|[${self.path}]|sending message $msg to $messageReceiver")
      messageReceiver ! msg
    }
  }

  def receive = Actor.emptyBehavior
}

object MessageSender {
  //pattern described in https://doc.akka.io/docs/akka/2.5/actors.html
  def props(messageReceiver: ActorRef) = Props(new MessageSender(messageReceiver))
}

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("exampleSystem")
    try {
      val receiver = system.actorOf(Props[MessageReceiver], "receiver")

      // Use companion object's props method to avoid a dangerous pattern described in
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

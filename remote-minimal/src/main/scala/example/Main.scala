package example

import java.io.{PrintWriter, StringWriter}

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class MessageReceiver extends Actor {
  def receive = {
    case s: String =>
      println(s"EchoActor: received message = $s")
  }
}

class MessageSender extends Actor {
  override def preStart(): Unit = {
    val path = "akka.tcp://receiverSystem@127.0.0.1:2551/user/receiver"
    val selection: ActorSelection = context.actorSelection(path)
    println(s"sending a message to $path")
    selection ! "Hello!!"
  }

  def receive = Actor.emptyBehavior
}

object Main {
  def startMessageReceiver(): Unit = {
    println("running startMessageReceiver()")
    val portConfig = ConfigFactory.load("receiver")
    val system = ActorSystem("receiverSystem", portConfig)
    println(s"provider = ${system.settings.config.getString("akka.actor.provider")}")
    println(s"listening at port = ${system.settings.config.getInt("akka.remote.netty.tcp.port")}")
    try {
      val ref = system.actorOf(Props[MessageReceiver], "receiver")
      println(s"started a receiver actor = ${ref}")
      // wait until user presses a key on console,
      // and exits immediately when a key is entered
      scala.io.StdIn.readLine()

      //https://groups.google.com/forum/#!topic/akka-user/eerWNwRQ7o0
//      EchoActor: received message = Hello!!
//      [ERROR] [01/25/2018 07:02:56.959] [receiverSystem-akka.remote.default-remote-dispatcher-14] [akka.tcp://receiverSystem@127.0.0.1:2551/system/endpointManager/reliableEndpointWriter-akka.tcp%3A%2F%2FsenderSystem%40127.0.0.1%3A2552-2/endpointWriter] AssociationError [akka.tcp://receiverSystem@127.0.0.1:2551] <- [akka.tcp://senderSystem@127.0.0.1:2552]: Error [Shut down address: akka.tcp://senderSystem@127.0.0.1:2552] [
//      akka.remote.ShutDownAssociation: Shut down address: akka.tcp://senderSystem@127.0.0.1:2552
//      Caused by: akka.remote.transport.Transport$InvalidAssociationException: The remote system terminated the association because it is shutting down.
//      ]
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

  def startMessageSender(): Unit = {
    println("running startMessageSender()")
    val portConfig = ConfigFactory.load("sender") //load src/main/resources/sender.conf
    val system = ActorSystem("senderSystem", portConfig)
    println(s"provider = ${system.settings.config.getString("akka.actor.provider")}")
    println(s"listening at port = ${system.settings.config.getInt("akka.remote.netty.tcp.port")}")
    try {
      system.actorOf(Props[MessageSender], "sender")
      Thread.sleep(3000) //wait enough until the actor is initialized and the message is sent
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

  def main(args: Array[String]): Unit = {
    println(s"Program args:")
    args.foreach(println)

    val receiverOrSender = args(0)
    if(receiverOrSender.toLowerCase == "receiver")
      startMessageReceiver()
    else if(receiverOrSender.toLowerCase == "sender")
      startMessageSender()
    else
      throw new Exception(s"receiverOrSender = $receiverOrSender is invalid input")
  }
}

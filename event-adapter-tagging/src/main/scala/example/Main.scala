package example

import akka.actor.{ActorSystem, Props}
import akka.persistence.PersistentActor
import com.typesafe.config.ConfigFactory

case class Command(i: Int)
case class Event(i: Int)

class MyPersistentActor extends PersistentActor {
  var sum: Int = 0

  override val persistenceId: String = "myPid"

  override def receiveRecover: Receive = {
    case Event(i) ⇒
      println(s"receiveRecover: Recovering an event = Event(${i})")
      sum += i
      println(s"receiveRecover: current state = ${sum}")
  }

  override def receiveCommand: Receive = {
    case Command(i) ⇒
      println(s"receiveCommand  : Received Command Cmd(${i})")
      persist(Event(i)) { event ⇒
        println(s"persist callback: Event = Event(${e.i}) persisted")
        sum += i
        println(s"persist callback: current state = ${sum}")
      }
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val system = ActorSystem("exampleSystem", config)

    try {
      val props = Props(new MyPersistentActor)
      val p1 = system.actorOf(props, "p1")
      p1 ! Command(1)
      p1 ! Command(2)
      p1 ! Command(3)
      Thread.sleep(3000)

    } finally {
      system.terminate()
    }
  }
}
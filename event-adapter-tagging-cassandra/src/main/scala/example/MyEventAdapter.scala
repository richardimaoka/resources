package example

import akka.actor.ExtendedActorSystem
import akka.persistence.journal.{EventAdapter, EventSeq, Tagged}

class MyEventAdapter(system: ExtendedActorSystem) extends EventAdapter {
  override def manifest(event: Any): String = "" // when no manifest needed, return ""

  override def toJournal(event: Any): Any = {
    val tags = Set("mytag1", "mytag2")
    println(s"EventAdapter    : toJournal called for event = $event, tags = $tags")
    Tagged(event, tags)
  }

  override def fromJournal(event: Any, manifest: String): EventSeq = {
    println(s"EventAdapter    : fromJournal called for event = $event and manifest = $manifest")
    EventSeq.single(event)
  }
}
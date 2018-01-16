blog post is https://richardimaoka.github.io/blog/event-adapter-cassandra

## Instruction to run the example
```
> git clone https://github.com/richardimaoka/resources.git
> cd resources
> cd event-adapter-tagging-cassandra
> sbt
> runMain example.Main
```

## Output 
```
> runMain example.Main
[info] Running example.Main
receiveCommand  : Received Command(1)
EventAdapter    : toJournal called for event = Event(1), tags = Set(mytag1, mytag2)
[WARN] [SECURITY][01/17/2018 05:59:44.106] [exampleSystem-cassandra-plugin-default-dispatcher-8] [akka.serialization.Serialization(akka://exampleSystem)] Using the default Java serializer for class [example.Event] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
persist callback: Event = Event(1) persisted
persist callback: current state = 1
receiveCommand  : Received Command(2)
EventAdapter    : toJournal called for event = Event(2), tags = Set(mytag1, mytag2)
persist callback: Event = Event(2) persisted
persist callback: current state = 3
receiveCommand  : Received Command(3)
EventAdapter    : toJournal called for event = Event(3), tags = Set(mytag1, mytag2)
persist callback: Event = Event(3) persisted
persist callback: current state = 6
[ERROR] [01/17/2018 05:59:45.538] [exampleSystem-akka.actor.default-dispatcher-12] [akka://exampleSystem/user/p1] exploded!
java.lang.Exception: exploded!
        at example.MyPersistentActor$$anonfun$receiveCommand$1.applyOrElse(Main.scala:37)
        at akka.actor.Actor.aroundReceive(Actor.scala:517)
        at akka.actor.Actor.aroundReceive$(Actor.scala:515)
        at example.MyPersistentActor.akka$persistence$Eventsourced$$super$aroundReceive(Main.scala:11)
        at akka.persistence.Eventsourced$$anon$1.stateReceive(Eventsourced.scala:680)
        at akka.persistence.Eventsourced.aroundReceive(Eventsourced.scala:192)
        at akka.persistence.Eventsourced.aroundReceive$(Eventsourced.scala:191)
        at example.MyPersistentActor.aroundReceive(Main.scala:11)
        at akka.actor.ActorCell.receiveMessage(ActorCell.scala:527)
        at akka.actor.ActorCell.invoke(ActorCell.scala:496)
        at akka.dispatch.Mailbox.processMailbox(Mailbox.scala:257)
        at akka.dispatch.Mailbox.run(Mailbox.scala:224)
        at akka.dispatch.Mailbox.exec(Mailbox.scala:234)
        at akka.dispatch.forkjoin.ForkJoinTask.doExec(ForkJoinTask.java:260)
        at akka.dispatch.forkjoin.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1339)
        at akka.dispatch.forkjoin.ForkJoinPool.runWorker(ForkJoinPool.java:1979)
        at akka.dispatch.forkjoin.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:107)

EventAdapter    : fromJournal called for event = Event(1) and manifest =
EventAdapter    : fromJournal called for event = Event(2) and manifest =
EventAdapter    : fromJournal called for event = Event(3) and manifest =
receiveRecover  : Recovering an event = Event(1)
receiveRecover  : current state = 1
receiveRecover  : Recovering an event = Event(2)
receiveRecover  : current state = 3
receiveRecover  : Recovering an event = Event(3)
receiveRecover  : current state = 6
receiveCommand  : Received Command(4)
EventAdapter    : toJournal called for event = Event(4), tags = Set(mytag1, mytag2)
persist callback: Event = Event(4) persisted
persist callback: current state = 10
receiveCommand  : Received Command(5)
EventAdapter    : toJournal called for event = Event(5), tags = Set(mytag1, mytag2)
persist callback: Event = Event(5) persisted
persist callback: current state = 15
[success] Total time: 12 s, completed Jan 17, 2018 5:59:52 AM
## References 

- Official documentation at https://doc.akka.io/docs/akka/2.5.9/persistence.html

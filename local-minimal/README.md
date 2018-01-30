blog posts are

- https://richardimaoka.github.io/blog/local-minimal-sender 
- https://richardimaoka.github.io/blog/local-minimal-receiver

## Instruction to run the example
```
> git clone https://github.com/richardimaoka/resources.git
> cd resources
> cd local-minimal
> sbt
> runMain example.Main
```

## Output 

Some `println` calls are inserted in the [complete example at GitHub](https://github.com/richardimaoka/resources/tree/master/local-minimal) to illustrate the behavior.

Thread names are shown as [exampleSystem-akka.actor.default-dispatcher-3] and [...-4].

```
[info] Running example.Main
provider = local
[exampleSystem-akka.actor.default-dispatcher-5] sending message Hello World to Actor[akka://exampleSystem/user/receiver#-846959521]
[exampleSystem-akka.actor.default-dispatcher-5] sending message Hello Universe to Actor[akka://exampleSystem/user/receiver#-846959521]
[exampleSystem-akka.actor.default-dispatcher-2] EchoActor: received message = Hello World
[exampleSystem-akka.actor.default-dispatcher-5] sending message Hello Galaxy to Actor[akka://exampleSystem/user/receiver#-846959521]
[exampleSystem-akka.actor.default-dispatcher-2] EchoActor: received message = Hello Universe
[exampleSystem-akka.actor.default-dispatcher-2] EchoActor: received message = Hello Galaxy
[success] Total time: 7 s, completed Jan 30, 2018 6:16:46 AM
```

## References 

- Official documentation of Akka Actor at https://doc.akka.io/docs/akka/2.5/actors.html
- Official documentation of Akka Dispatcher at https://doc.akka.io/docs/akka/2.5/dispatchers.html
- Official documentation of Akka lifecycle at https://doc.akka.io/docs/akka/current/actors.html$actor-lifecycle
- Official documentation of Akka Mailbox at https://doc.akka.io/docs/akka/2.5/mailboxes.html?language=scala#mailboxes)
- Official documentation of Akka location transparency at https://doc.akka.io/docs/akka/current/general/remoting.html#location-transparency
- Oracle's documentation about Fork/Join at https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html
- ExecutorService Javadoc at https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html

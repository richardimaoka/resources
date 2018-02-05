runMain example.executioncontext.Main
sbt:executor-etc> runMain example.future.Main
sbt:Hello> runMain example.executor.Main


```
[info] Compiling 1 Scala source to C:\Users\nishyu\resources\executor-and-execution-context\target\scala-2.12\classes ...
[info] Done compiling.
[warn] Multiple main classes detected.  Run 'show discoveredMainClasses' to see the list
[info] Packaging C:\Users\nishyu\resources\executor-and-execution-context\target\scala-2.12\executor-and-execution-context_2.12-0.1.0-SNAPSHOT.jar ...
[info] Done packaging.
[info] Running example.thread.Main
[Thread[run-main-1,5,run-main-group-1]] - main thread
[Thread[Thread-4,5,run-main-group-1]] - PrintRunnable(2) run() is executed
[Thread[Thread-3,5,run-main-group-1]] - PrintRunnable(1) run() is executed
[Thread[Thread-6,5,run-main-group-1]] - PrintRunnable(4) run() is executed
[Thread[Thread-8,5,run-main-group-1]] - PrintRunnable(6) run() is executed
[Thread[Thread-5,5,run-main-group-1]] - PrintRunnable(3) run() is executed
[Thread[Thread-9,5,run-main-group-1]] - PrintRunnable(7) run() is executed
[Thread[Thread-12,5,run-main-group-1]] - PrintRunnable(10) run() is executed
[Thread[Thread-7,5,run-main-group-1]] - PrintRunnable(5) run() is executed
[Thread[Thread-11,5,run-main-group-1]] - PrintRunnable(9) run() is executed
[Thread[Thread-13,5,run-main-group-1]] - PrintRunnable(11) run() is executed
[Thread[Thread-10,5,run-main-group-1]] - PrintRunnable(8) run() is executed
[Thread[Thread-14,5,run-main-group-1]] - PrintRunnable(12) run() is executed
[success] Total time: 1 s, completed Feb 4, 2018 5:01:20 PM
```



```
[info] Running example.executor.Main
[Thread[run-main-0,5,run-main-group-0]] - main thread
[Thread[pool-8-thread-1,5,run-main-group-0]] - PrintRunnable(1) run() is executed
[Thread[pool-8-thread-2,5,run-main-group-0]] - PrintRunnable(2) run() is executed
[Thread[pool-8-thread-3,5,run-main-group-0]] - PrintRunnable(3) run() is executed
[Thread[pool-8-thread-1,5,run-main-group-0]] - PrintRunnable(6) run() is executed
[Thread[pool-8-thread-4,5,run-main-group-0]] - PrintRunnable(4) run() is executed
[Thread[pool-8-thread-2,5,run-main-group-0]] - PrintRunnable(7) run() is executed
[Thread[pool-8-thread-3,5,run-main-group-0]] - PrintRunnable(8) run() is executed
[Thread[pool-8-thread-5,5,run-main-group-0]] - PrintRunnable(5) run() is executed
[Thread[pool-8-thread-2,5,run-main-group-0]] - PrintRunnable(11) run() is executed
[Thread[pool-8-thread-4,5,run-main-group-0]] - PrintRunnable(10) run() is executed
[Thread[pool-8-thread-1,5,run-main-group-0]] - PrintRunnable(9) run() is executed
[Thread[pool-8-thread-3,5,run-main-group-0]] - PrintRunnable(12) run() is executed
[success] Total time: 15 s, completed Feb 4, 2018 3:26:02 PM
```


```
[info] Running example.executioncontext.Main
[Thread[run-main-2,5,run-main-group-2]] - main thread
[Thread[scala-execution-context-global-168,5,main]] - PrintRunnable(2) run() is executed
[Thread[scala-execution-context-global-128,5,main]] - PrintRunnable(1) run() is executed
[Thread[scala-execution-context-global-169,5,main]] - PrintRunnable(3) run() is executed
[Thread[scala-execution-context-global-184,5,main]] - PrintRunnable(7) run() is executed
[Thread[scala-execution-context-global-131,5,main]] - PrintRunnable(4) run() is executed
[Thread[scala-execution-context-global-184,5,main]] - PrintRunnable(10) run() is executed
[Thread[scala-execution-context-global-168,5,main]] - PrintRunnable(5) run() is executed
[Thread[scala-execution-context-global-185,5,main]] - PrintRunnable(9) run() is executed
[Thread[scala-execution-context-global-169,5,main]] - PrintRunnable(8) run() is executed
[Thread[scala-execution-context-global-184,5,main]] - PrintRunnable(12) run() is executed
[Thread[scala-execution-context-global-128,5,main]] - PrintRunnable(6) run() is executed
[Thread[scala-execution-context-global-131,5,main]] - PrintRunnable(11) run() is executed
[success] Total time: 3 s, completed Feb 4, 2018 3:58:25 PM
```


```
[info] Running example.future.Main
[Thread[run-main-3,5,run-main-group-3]] - main thread
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(1) is executed
[Thread[scala-execution-context-global-199,5,main]] - printThreadName(2) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(3) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(4) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(5) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(6) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(7) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(8) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(9) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(10) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(11) is executed
[Thread[scala-execution-context-global-200,5,main]] - printThreadName(12) is executed
[success] Total time: 3 s, completed Feb 4, 2018 4:40:30 PM
```



```
sbt:executor-and-execution-context> runMain example.dispatcher.Main
[info] Compiling 1 Scala source to C:\Users\nishyu\resources\executor-and-execution-context\target\scala-2.12\classes ...
[info] Done compiling.
[warn] Multiple main classes detected.  Run 'show discoveredMainClasses' to see the list
[info] Packaging C:\Users\nishyu\resources\executor-and-execution-context\target\scala-2.12\executor-and-execution-context_2.12-0.1.0-SNAPSHOT.jar ...
[info] Done packaging.
[info] Running example.dispatcher.Main
[Thread[run-main-0,5,run-main-group-0]] - main thread
[Thread[exampleSystem-akka.actor.default-dispatcher-3,5,run-main-group-0]] - printThreadName(2) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-4,5,run-main-group-0]] - printThreadName(3) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-2,5,run-main-group-0]] - printThreadName(4) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-5,5,run-main-group-0]] - printThreadName(1) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-4,5,run-main-group-0]] - printThreadName(6) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-3,5,run-main-group-0]] - printThreadName(5) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-7,5,run-main-group-0]] - printThreadName(7) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-7,5,run-main-group-0]] - printThreadName(8) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-7,5,run-main-group-0]] - printThreadName(9) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-7,5,run-main-group-0]] - printThreadName(10) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-7,5,run-main-group-0]] - printThreadName(11) is executed
[Thread[exampleSystem-akka.actor.default-dispatcher-7,5,run-main-group-0]] - printThreadName(12) is executed
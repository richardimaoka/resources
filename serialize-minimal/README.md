inheritance - writeReplace in parent class -> not called, confirm it -> separate project??
composition - a class implementing writeReplace inside a case class as a parameter
figure out the sequence of writeReplace, etc
https://docs.oracle.com/javase/jp/8/docs/platform/serialization/spec/output.html


writeReplace to return a wrong object (e.g. String, instead of ActorRef) will produce an error:
    writeReplace in ActorRefAdapter[-T] called producing akka://example/user/empty#495066247
    cannot assign instance of java.lang.String to field example.Main$AAA$3.ref of type akka.actor.typed.ActorRef in instance of example.Main$AAA$3
    java.lang.ClassCastException: cannot assign instance of java.lang.String to field example.Main$AAA$3.ref of type akka.actor.typed.ActorRef in instance of example.Main$AAA$3
            at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2089)
            at java.io.ObjectStreamClass.setObjFieldValues(ObjectStreamClass.java:1261)
            at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2006)
            at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1924)
            at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1801)
            at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1351)
            at java.io.ObjectInputStream.readObject(ObjectInputStream.java:371)
            at akka.serialization.JavaSerializer.$anonfun$fromBinary$1(Serializer.scala:327)
            at scala.util.DynamicVariable.withValue(DynamicVariable.scala:58)
            at akka.serialization.JavaSerializer.fromBinary(Serializer.scala:327)
            at example.Main$.main(Main.scala:37)
            at example.Main.main(Main.scala)
            at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
            at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
            at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
            at java.lang.reflect.Method.invoke(Method.java:497)
            at sbt.Run.invokeMain(Run.scala:67)
            at sbt.Run.run0(Run.scala:61)
            at sbt.Run.sbt$Run$$execute$1(Run.scala:51)
            at sbt.Run$$anonfun$run$1.apply$mcV$sp(Run.scala:55)
            at sbt.Run$$anonfun$run$1.apply(Run.scala:55)
            at sbt.Run$$anonfun$run$1.apply(Run.scala:55)
            at sbt.Logger$$anon$4.apply(Logger.scala:84)
            at sbt.TrapExit$App.run(TrapExit.scala:248)
            at java.lang.Thread.run(Thread.java:745)
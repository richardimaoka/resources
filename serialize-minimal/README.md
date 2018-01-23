blog post is https://richardimaoka.github.io/blog/serializer-minimal

## Instruction to run the example
```
> git clone https://github.com/richardimaoka/resources.git
> cd resources
> cd serialize-minimal
> sbt
> runMain example.Main
```

## Output 

Some `println` calls are inserted in the [complete example at GitHub](https://github.com/richardimaoka/resources/tree/master/serialize-minimal) to illustrate the behavior

```
[info] Running example.Main
Serializer for class example.MyMessage = example.MySerializer@254b2a65
MySerializer: toBinary(MyMessage(aaa,bbb)) is called
MySerializer: fromBinary(979797124989898) is called
original = MyMessage(aaa,bbb), class = class example.MyMessage
restored = MyMessage(aaa,bbb), class = class example.MyMessage
[success] Total time: 1 s, completed Jan 23, 2018 9:48:55 PM
```

## References 

- Official documentation of Akka serialization at https://doc.akka.io/docs/akka/2.5/serialization.html
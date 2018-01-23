package example

import java.io.{PrintWriter, StringWriter}
import java.nio.charset.StandardCharsets

import akka.serialization.{SerializationExtension, Serializer}
import com.typesafe.config.ConfigFactory

case class MyMessage(str1: String, str2: String)

class MySerializer extends Serializer {
  //manifest helps you upgrade the "version" of your serializer, which is not needed for this example
  def includeManifest: Boolean = false

  def identifier = 9999

  def toBinary(obj: AnyRef): Array[Byte] = {
    println(s"MySerializer: toBinary($obj) is called")
    obj match {
      case msg: MyMessage => (msg.str1 + "|" + msg.str2).getBytes(StandardCharsets.UTF_8)
    }
  }

  def fromBinary(bytes: Array[Byte], clazz: Option[Class[_]]): AnyRef = {
    println(s"MySerializer: fromBinary(${stringOfArrayByte(bytes)}) is called")
    val repString = new String(bytes, StandardCharsets.UTF_8)
    val arr: Array[String] = repString.split('|') // '|' is enclosed in single quotes = Char, not String
    new MyMessage(arr(0), arr(1))
  }

  private def stringOfArrayByte(bytes: Array[Byte]): String = {
    val builder = new StringBuilder
    for(byte <- bytes)
      builder.append(byte)
    builder.toString()
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val system = akka.actor.ActorSystem("example", ConfigFactory.load())
    try {
      val original = MyMessage("aaa", "bbb")

      // This is simplified version of what happens inside Akka when it
      // to serialize a message
      // 1: get serialization extension of the current ActorSystem
      // 2: find the serializer for the messsage by `findSerializerFor` method
      // 3: perform serialization by `toBinary
      val serialization = SerializationExtension(system)
      val serializer = serialization.findSerializerFor(original)
      println(s"Serializer for ${original.getClass} = " + serializer)
      val bytes = serializer.toBinary(original)
      val restored = serializer.fromBinary(bytes, manifest = None)

      // Voilá!
      println(s"original = $original, class = ${original.getClass}")
      println(s"restored = $restored, class = ${restored.getClass}")

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

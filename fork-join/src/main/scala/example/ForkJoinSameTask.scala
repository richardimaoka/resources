package example

import java.io.{PrintWriter, StringWriter}
import java.util.concurrent.{ForkJoinPool, ForkJoinTask}

class MyFJT extends ForkJoinTask[Unit] with Runnable{
  override def run(): Unit = {
    println("MyFJT run() called")
  }
  def exec(): Boolean = { run(); true }

  //these are necessary implementation for compilation, but not actually used here
  def getRawResult(): Unit = ???
  def setRawResult(unit: Unit): Unit = ???
}

object ForkJoinSameTask {
  def main(args: Array[String]): Unit = {
     try {
       val pool = new ForkJoinPool()
       val task: MyFJT = new MyFJT
       pool.execute(task: Runnable)
       pool.execute(task: Runnable)
       pool.execute(task: Runnable)
       pool.execute(task: Runnable)
       pool.execute(task: Runnable)
       Thread.sleep(1000)
       // You will get the below output (i.e.) only two
       // Supposedly you should get only one, but due to race condition(?),
       // the second output is also generated in my environment
       // MyFJT run() called
       // MyFJT run() called
     } catch {
      case t: Throwable => {
        val sw = new StringWriter
        t.printStackTrace(new PrintWriter(sw))
        println(t.getMessage)
        println(sw)
      }
    }
  }
}

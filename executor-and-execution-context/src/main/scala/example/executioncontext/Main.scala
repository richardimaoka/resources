package example.executioncontext

import scala.concurrent.ExecutionContext

class PrintRunnable(id: Int) extends Runnable {
  def run(): Unit = {
    println(s"[${Thread.currentThread()}] - PrintRunnable($id) run() is executed")
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    println(s"[${Thread.currentThread()}] - main thread")

    // ExecutionContext.Implicits.global is the (kind of) "default" ExecutionContext
    // which you wouldn't want to use in production,
    val executionContext: ExecutionContext = ExecutionContext.Implicits.global

    // Usually you don't call execute method of ExecutionContext by yourself.
    // However, this example is just to show its similarity to Executor in Java
    executionContext.execute(new PrintRunnable(1))
    executionContext.execute(new PrintRunnable(2))
    executionContext.execute(new PrintRunnable(3))
    executionContext.execute(new PrintRunnable(4))
    executionContext.execute(new PrintRunnable(5))
    executionContext.execute(new PrintRunnable(6))
    executionContext.execute(new PrintRunnable(7))
    executionContext.execute(new PrintRunnable(8))
    executionContext.execute(new PrintRunnable(9))
    executionContext.execute(new PrintRunnable(10))
    executionContext.execute(new PrintRunnable(11))
    executionContext.execute(new PrintRunnable(12))

    //give enough time all threads complete their `Runnable`s
    Thread.sleep(2000)
  }
}
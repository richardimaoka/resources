package example.executioncontext

import java.util.concurrent.Executors

import example.executor

import scala.concurrent.ExecutionContext

class PrintRunnable(id: Int) extends Runnable {
  def run(): Unit = {
    println(s"[${Thread.currentThread()}] - PrintRunnable($id) run() is executed")
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    println(s"[${Thread.currentThread()}] - main thread")
    
    // The "default" ExecutionContext which you wouldn't want to use in production, 
    // since you would most likely configure the thread pool backing the ExecutionContext for your application.
    // 
    // For this simple example, ok to use
    val executionContext: ExecutionContext = ExecutionContext.Implicits.global
    
    executionContext.execute(new executor.PrintRunnable(1))
    executionContext.execute(new executor.PrintRunnable(2))
    executionContext.execute(new executor.PrintRunnable(3))
    executionContext.execute(new executor.PrintRunnable(4))
    executionContext.execute(new executor.PrintRunnable(5))
    executionContext.execute(new executor.PrintRunnable(6))
    executionContext.execute(new executor.PrintRunnable(7))
    executionContext.execute(new executor.PrintRunnable(8))
    executionContext.execute(new executor.PrintRunnable(9))
    executionContext.execute(new executor.PrintRunnable(10))
    executionContext.execute(new executor.PrintRunnable(11))
    executionContext.execute(new executor.PrintRunnable(12))

    //give enough time all threads complete their `Runnable`s
    Thread.sleep(2000)
  }
}
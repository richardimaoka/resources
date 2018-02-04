package example.executor

import java.util.concurrent.Executors

class PrintRunnable(id: Int) extends Runnable {
  def run(): Unit = {
    println(s"[${Thread.currentThread()}] - PrintRunnable($id) run() is executed")
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    println(s"[${Thread.currentThread()}] - main thread")
    val executor = Executors.newFixedThreadPool(5);
    executor.execute(new PrintRunnable(1))
    executor.execute(new PrintRunnable(2))
    executor.execute(new PrintRunnable(3))
    executor.execute(new PrintRunnable(4))
    executor.execute(new PrintRunnable(5))
    executor.execute(new PrintRunnable(6))
    executor.execute(new PrintRunnable(7))
    executor.execute(new PrintRunnable(8))
    executor.execute(new PrintRunnable(9))
    executor.execute(new PrintRunnable(10))
    executor.execute(new PrintRunnable(11))
    executor.execute(new PrintRunnable(12))

    //give all threads enough time completing their `Runnable`s
    Thread.sleep(2000)

    //Javadoc of this method says:
    /**
      * This method does not wait for previously submitted tasks to
      * complete execution.  Use {@link #awaitTermination awaitTermination}
      * to do that
      */
    //but using this method for simplicity
    executor.shutdown()
  }
}
package example.thread

class PrintRunnable(id: Int) extends Runnable {
  def run(): Unit = {
    println(s"[${Thread.currentThread()}] - PrintRunnable($id) run() is executed")
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    println(s"[${Thread.currentThread()}] - main thread")

    val t1  = new Thread(new PrintRunnable(1))
    val t2  = new Thread(new PrintRunnable(2))
    val t3  = new Thread(new PrintRunnable(3))
    val t4  = new Thread(new PrintRunnable(4))
    val t5  = new Thread(new PrintRunnable(5))
    val t6  = new Thread(new PrintRunnable(6))
    val t7  = new Thread(new PrintRunnable(7))
    val t8  = new Thread(new PrintRunnable(8))
    val t9  = new Thread(new PrintRunnable(9))
    val t10 = new Thread(new PrintRunnable(10))
    val t11 = new Thread(new PrintRunnable(11))
    val t12 = new Thread(new PrintRunnable(12))

    //start threads
    t1.start()
    t2.start()
    t3.start()
    t4.start()
    t5.start()
    t6.start()
    t7.start()
    t8.start()
    t9.start()
    t10.start()
    t11.start()
    t12.start()
    
    //wait for threads to die
    t1.join()
    t2.join()
    t3.join()
    t4.join()
    t5.join()
    t6.join()
    t7.join()
    t8.join()
    t9.join()
    t10.join()
    t11.join()
    t12.join()
  }
}
package example

import java.io.{PrintWriter, StringWriter}
import java.util.concurrent.Executors

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("exampleSystem")
    try {
      //println(system.dispatchers.lookup("my-dispatcher1"))

      //println(system.dispatchers.lookup("my-dispatcher2"))
      //[WARN] [02/06/2018 04:25:52.628] [run-main-9] [PinnedDispatcherConfigurator] PinnedDispatcher [my-dispatcher2] not configured to use ThreadPoolExecutor, falling back to default config.

      //println(system.dispatchers.lookup("my-dispatcher3"))
      //Cannot instantiate ExecutorServiceConfigurator ("executor = [fork-join-executorX]"), defined in [my-dispatcher3],

      println(system.dispatchers.lookup("my-dispatcher4"))
      // this works without any exception, but is *NOT* using the following configuration
      //   fork-join-executorXX {
      //     # Min number of threads to cap factor-based parallelism number to
      //       parallelism-min = 2
      //     # Parallelism (threads) ... ceil(available processors * factor)
      //     parallelism-factor = 2.0
      //     # Max number of threads to cap factor-based parallelism number to
      //     parallelism-max = 10
      //   }
      // but using the default fork-join-executor settings from akka/actor's reference.conf

      val executor = Executors.newFixedThreadPool(5)
      val execContext = ExecutionContext.fromExecutor(executor)
      val system2 = ActorSystem("example2", None, None, Some(execContext))
      //the following (case Some(ec)) is used when passing-in default ec
//      class DefaultExecutorServiceConfigurator(config: Config, prerequisites: DispatcherPrerequisites, fallback: ExecutorServiceConfigurator) extends ExecutorServiceConfigurator(config, prerequisites) {
//        val provider: ExecutorServiceFactoryProvider =
//          prerequisites.defaultExecutionContext match {
//            case Some(ec) ⇒
//              prerequisites.eventStream.publish(Debug("DefaultExecutorServiceConfigurator", this.getClass, s"Using passed in ExecutionContext as default executor for this ActorSystem. If you want to use a different executor, please specify one in akka.actor.default-dispatcher.default-executor."))
//
//              new AbstractExecutorService with ExecutorServiceFactory with ExecutorServiceFactoryProvider {
//                def createExecutorServiceFactory(id: String, threadFactory: ThreadFactory): ExecutorServiceFactory = this
//                def createExecutorService: ExecutorService = this
//                def shutdown(): Unit = ()
//                def isTerminated: Boolean = false
//                def awaitTermination(timeout: Long, unit: TimeUnit): Boolean = false
//                def shutdownNow(): ju.List[Runnable] = ju.Collections.emptyList()
//                def execute(command: Runnable): Unit = ec.execute(command)
//                def isShutdown: Boolean = false
//              }
//            case None ⇒ fallback
//          }
//
//        def createExecutorServiceFactory(id: String, threadFactory: ThreadFactory): ExecutorServiceFactory =
//          provider.createExecutorServiceFactory(id, threadFactory)
//      }

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

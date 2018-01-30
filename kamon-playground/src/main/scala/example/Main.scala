package example

import kamon.Kamon
import kamon.metric.{Entity, EntityRecorderFactory, GenericEntityRecorder}
import kamon.metric.instrument.{Histogram, InstrumentFactory, Time}


object Main  {

  /**
   * http://kamon.io/documentation/0.6.x/kamon-core/metrics/core-concepts/
   *
   * All entities have three identifying properties:
   *
   * * a name,
   *     that should be unique among entities in the same category. (this contradicts with the later explanation about entity with only tags difference?)
   *     In the thread pools monitoring case mentioned above you might have a thread pool named “jdbc-pool”
   *     and another one named “netty-pool” and both will have their own, separate entity recorders.
   *
   * * a category,
   *     that should be shared among similar entities. Following the example, both “jdbc-pool” and “netty-pool”
   *     have the same set of metrics (pool size, number of active threads, etc.)
   *     and should share the same category of “thread-pool”.
   *
   * * tags,
   *     which are optional. The tags are a simple map of String keys to String values,
   *     used to provide dimensions that encode additional information about the entity being tracked.
   */
  def tagComparison(): Unit ={
    println("tagComparison -------------------------------------")
    val entity1 = Entity("entityName1", "entityCategory1")
    val entity2 = Entity("entityName1", "entityCategory1")

    if(entity1 == entity2)
      println(s"$entity1 == $entity2")
    else
      println(s"$entity1 != $entity2")

    /**
     * http://kamon.io/documentation/0.6.x/kamon-core/metrics/core-concepts/
     *
     * If you have two entities with the same name and category but different tags
     * then they are effectively two different entities in Kamon,
     */
    val entity3 = Entity("entityName1", "entityCategory1", Map("a" -> "b"))

    if(entity1 == entity3)
      println(s"$entity1 == $entity3")
    else
      println(s"$entity1 != $entity3")
  }

  def entityFactorStuff(): Unit = {
    // http://kamon.io/documentation/0.6.x/kamon-core/metrics/recording-metrics/#using-entity-recorders
    // Define your EntityRecorder (although the name says ActorMetrics...) combining up different metrics
    class ActorMetrics(instrumentFactory: InstrumentFactory) extends GenericEntityRecorder(instrumentFactory) {
      val timeInMailbox = Kamon.metrics.histogram("time-in-mailbox", Time.Nanoseconds)
      val processingTime = Kamon.metrics.histogram("processing-time", Time.Nanoseconds)
      val mailboxSize = minMaxCounter("mailbox-size")
      val errors = counter("errors")
    }

    object ActorMetrics extends EntityRecorderFactory[ActorMetrics] {
      def category: String = "actor"
      def createRecorder(instrumentFactory: InstrumentFactory): ActorMetrics = new ActorMetrics(instrumentFactory)
    }

    val myManagedActorMetrics = Kamon.metrics.entity(ActorMetrics, "my-managed-actor")

    myManagedActorMetrics.mailboxSize.increment()
    myManagedActorMetrics.processingTime.record(42)
    myManagedActorMetrics.mailboxSize.decrement()
  }

  def main(args: Array[String]): Unit = {
    Kamon.start()

    tagComparison()

    println("main stuff -------------------------------------")

    //Histogram extends Instrument
    val myHistogram: Histogram  = Kamon.metrics.histogram("my.histogram")
    // This method call does three things:
    //
    // 1. creates an Entity with
    //     name     = "my.histogram"
    //     category = histogram
    //     tags     = empty
    //   as done in:
    //     val counterEntity = Entity(name, SingleInstrumentEntityRecorder.Counter, tags ++ defaultTags)
    //
    // 2. create an EntityRecorder ...
    //
    // 3. ... with an Instrument instance, created by instrumentFactory
    //   val factory = instrumentFactory(histogramEntity.category)
    //
    // the full function below
    //
    //    def registerHistogram(name: String, tags: Map[String, String], unitOfMeasurement: Option[UnitOfMeasurement],
    //      dynamicRange: Option[DynamicRange]): Histogram = {
    //
    //      val histogramEntity = Entity(name, SingleInstrumentEntityRecorder.Histogram, tags ++ defaultTags)
    //      val recorder = _trackedEntities.atomicGetOrElseUpdate(histogramEntity, {
    //        val factory = instrumentFactory(histogramEntity.category)
    //        HistogramRecorder(
    //          HistogramKey(histogramEntity.category, unitOfMeasurement.getOrElse(UnitOfMeasurement.Unknown)),
    //          factory.createHistogram(name, dynamicRange)
    //        )
    //      }, _.cleanup)
    //
    //      recorder.asInstanceOf[HistogramRecorder].instrument
    //    }
    //
    //  The returned value is an instance of Instrument, as described in
    //
    //    http://kamon.io/documentation/0.6.x/kamon-core/metrics/recording-metrics/
    //
    //  When using these simple entity recorders you will not get back an instance of the internal EntityRecorder
    //  implementation but rather the plain instrument that you requested.
    //  Kamon still has that single instrument wrapped in a EntityRecorder internally, but that is just an implementation detail.

    val myCounter = Kamon.metrics.counter("my.counter")
    val myTaggedCounter = Kamon.metrics.counter("my.tagged.counter")

    myHistogram.record(42)
    myHistogram.record(50)
    myCounter.increment()
    myTaggedCounter.increment()

    //Histogram extends Instrument
    val myHistogram_copy: Histogram = Kamon.metrics.histogram("my.histogram")
    val myHistogram_2:    Histogram = Kamon.metrics.histogram("my.histogram_2")

    if(myHistogram == myHistogram_copy)
      println(s"myHistogram($myHistogram) == myHistogram_copy($myHistogram_copy)")
    else
      println(s"myHistogram($myHistogram) != myHistogram_copy($myHistogram_copy)")

    if(myHistogram == myHistogram_2)
      println(s"myHistogram($myHistogram) == myHistogram_2($myHistogram_2)")
    else
      println(s"myHistogram($myHistogram) != myHistogram_2($myHistogram_2)")

    Kamon.shutdown()

  }
}

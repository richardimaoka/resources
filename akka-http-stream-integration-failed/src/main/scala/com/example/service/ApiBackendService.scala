//package com.example.service
//
//import akka.actor.{Actor, ActorRef}
//import akka.pattern.ask
//import akka.stream.{Materializer, OverflowStrategy}
//import akka.stream.scaladsl.{Sink, Source}
//import com.example.model.{EnrichedUser, User}
//
//import scala.concurrent.{Future, Promise}
//
//class ApiBackendActor(service: Any)(implicit materializer: Materializer) extends Actor {
//  val actor: ActorRef =
//    Source.actorRef(4, OverflowStrategy.dropNew)
//      .to(Sink.actorRef(self(), "completed"))
//      .run()
//
////
////  def receive = {
////    //input
////    case u: User =>
////      actor ! u
////    case u: EnrichedUser =>
////      service ! u
////  }
//}
//
//class ApiBackendService(actor: ActorRef) {
//
//
////  def handleUserCreationRequest(user: User) : Future[EnrichedUser] = {
////    val promise = Promise[EnrichedUser]
////  }
//}

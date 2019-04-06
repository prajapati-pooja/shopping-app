package com.shopping.app

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor

object AppConfig {
  implicit val system: ActorSystem = ActorSystem("shoppingApp")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
}

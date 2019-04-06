package com.shopping.app

import akka.http.scaladsl.Http
import com.google.inject.Guice.createInjector
import com.shopping.users.{UserModule, UsersRoute}

object Application extends App {
  import AppConfig._

  private val host = "0.0.0.0"
  private val port = 9000

  private val module = new UserModule
  private val userRoutes: UsersRoute = createInjector(module).getInstance(classOf[UsersRoute])

  Http().bindAndHandle(userRoutes.routes, host, port)
}

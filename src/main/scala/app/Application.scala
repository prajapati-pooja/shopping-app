package app

import akka.http.scaladsl.Http
import commons.db.DbClient
import users.{UserRepository, UsersRoute}

object Application extends App {
  import AppConfig._

  private val host = "0.0.0.0"
  private val port = 9000

  private val client = new DbClient
  private val repository = new UserRepository(client)
  private val userRoutes = new UsersRoute(repository)

  Http().bindAndHandle(userRoutes.routes, host, port)
}

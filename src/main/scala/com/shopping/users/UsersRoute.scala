package com.shopping.users

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive1, Route}
import com.google.inject.Inject
import com.shopping.users.User.userFormat
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

class UsersRoute @Inject()(userService: UserService) extends PlayJsonSupport {
  val routes: Route = {
    path("users") {
      get {
        extractParam { userParams =>
          {
            val eventualUsers = userService.getUsers(userParams)
            onSuccess(eventualUsers) {
              case Right(users) => complete(users)
              case Left(error)  => complete(error)
            }
          }
        }
      }
    } ~
      path("users" / "totalorders") {
        get {
          val totalOrders = userService.getTotalOrders
          onSuccess(totalOrders) {
            case Right(orders) => complete(orders)
            case Left(error)   => complete(error)
          }
        }
      }

  }

  def extractParam: Directive1[UserParams] = {
    parameter('age.as[Int].?, 'sortBy.as[String].?, 'sortOrder.as[String].?)
      .as(UserParams.create _)
  }
}

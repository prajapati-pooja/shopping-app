package com.shopping.users

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.Inject
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import com.shopping.users.User.userFormat

class UsersRoute @Inject()(userService: UserService) extends PlayJsonSupport {
  val routes: Route = {
    path("users") {
      get {
        parameter('age.as[Int].?) { age =>
          {
            val eventualUsers = userService.getUsers(validate(age))
            onSuccess(eventualUsers) {
              case Right(users) => complete(users)
              case Left(error)  => complete(error)
            }
          }
        }
      }
    }
  }

  private def validate(age: Option[Int]): Option[Int] = {
    age.map(_.toString.trim).filter(_.length != 0).map(_.toInt)
  }
}

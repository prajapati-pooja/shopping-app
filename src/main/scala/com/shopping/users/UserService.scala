package com.shopping.users

import com.google.inject.{Inject, Singleton}
import com.shopping.commons.models.ErrorBody

import scala.concurrent.Future
import com.shopping.app.AppConfig._

@Singleton
class UserService @Inject()(repository: UserRepository) {

  def getUsers(age: Option[Int]): Future[Either[ErrorBody, Seq[User]]] = {
    repository.getUsers.map(mayBeUsers => {
      mayBeUsers.map(users => {
        age.map(a => users.filter(user => a.equals(user.age))).getOrElse(users)
      })
    })
  }

}

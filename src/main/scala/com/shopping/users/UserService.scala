package com.shopping.users

import com.google.inject.{Inject, Singleton}
import com.shopping.app.AppConfig._
import com.shopping.commons.models.ErrorBody

import scala.concurrent.Future

@Singleton
class UserService @Inject()(repository: UserRepository) {

  def getUsers(params: UserParams): Future[Either[ErrorBody, Seq[User]]] = {
    repository.getUsers.map(mayBeUsers => {
      mayBeUsers.map(users => {
        params.age.map(a => users.filter(user => a.equals(user.age))).getOrElse(users)
      })
    })
  }

}

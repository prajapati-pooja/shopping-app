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
        val mayBeFilteredUsers = params.age
          .map(a => users.filter(user => a.equals(user.age)))
          .getOrElse(users)

        sortUsers(params, mayBeFilteredUsers)
      })
    })
  }

  def getTotalOrders: Future[Either[ErrorBody, List[String]]] = {
    val users = getUsers(UserParams(None, None, None))
    users.map(mayBeUsers => {
      mayBeUsers.map(allUser => {
        allUser.flatMap(user => user.order).toList
      })
    })
  }

  private def sortUsers(params: UserParams, users: Seq[User]): Seq[User] = {
    val sortByField: Option[User => Int] =
      params.sortBy.flatMap(s => extract(s))

    val sortOrderField: Option[Ordering[Int]] = params.sortOrder
      .filter(s => "DESC".equals(s))
      .map(_ => Ordering[Int].reverse)

    sortByField
      .map(field =>
        users.sortBy(field)(sortOrderField.getOrElse(Ordering[Int])))
      .getOrElse(users)
  }

  private def extract(field: String): Option[User => Int] =
    if ("AGE".equals(field)) Some((u: User) => u.age) else None

}

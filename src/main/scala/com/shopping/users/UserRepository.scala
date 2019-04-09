package com.shopping.users

import com.google.inject.Inject
import com.shopping.commons.db.DbClient
import com.shopping.commons.models.ErrorBody
import javax.inject.Singleton
import org.mongodb.scala.Completed
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}


@Singleton
class UserRepository @Inject()(dbClient: DbClient) {

  def getUsers: Future[Either[ErrorBody, Seq[User]]] = {
    import com.shopping.app.AppConfig.executionContext

    val eventualUsers: Future[Seq[JsValue]] = dbClient.getUserCollection

    eventualUsers.map(users => {
      Try(users.map(user => user.validate[User].get)) match {
        case Success(parsedUsers) => Right(parsedUsers)
        case Failure(throwable) =>
          Left(ErrorBody("parsing error", throwable.getMessage))
      }
    })
  }

  def insertUsers(users: List[User]): List[Future[Completed]] = {
    users.map(user => dbClient.insertUser(Json.toJson(user)))
  }
}

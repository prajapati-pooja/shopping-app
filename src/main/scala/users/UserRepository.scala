package users

import commons.db.DbClient
import commons.models.ErrorBody
import play.api.libs.json.JsValue

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class UserRepository(dbClient: DbClient) {

  def getUsers: Future[Either[ErrorBody, Seq[User]]] = {
    import app.AppConfig.executionContext

    val eventualUsers: Future[Seq[JsValue]] = dbClient.getUserCollection

    eventualUsers.map(users => {
      Try(users.map(user => user.validate[User].get)) match {
        case Success(parsedUsers) => Right(parsedUsers)
        case Failure(throwable) =>
          Left(ErrorBody("parsing error", throwable.getMessage))
      }
    })
  }
}

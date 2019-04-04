package users

import com.google.inject.Inject
import commons.db.DbClient
import commons.models.ErrorBody
import javax.inject.Singleton
import play.api.libs.json.JsValue

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

trait Repository

@Singleton
class UserRepository @Inject()(dbClient: DbClient) extends Repository {

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

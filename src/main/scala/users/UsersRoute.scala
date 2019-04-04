package users

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.Inject
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import users.User.userFormat

class UsersRoute @Inject()(userRepository: UserRepository) extends PlayJsonSupport {
  val routes: Route = {
    path("users") {
      get {
        val eventualUsers = userRepository.getUsers
        onSuccess(eventualUsers) {
          case Right(users) => complete(users)
          case Left(error)  => complete(error)
        }
      }
    }
  }
}

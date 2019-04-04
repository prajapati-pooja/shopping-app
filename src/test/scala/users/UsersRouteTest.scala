package users

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

import scala.concurrent.ExecutionContext
import scala.concurrent.Future.successful


class UsersRouteTest extends FunSuite with Matchers with ScalatestRouteTest with BeforeAndAfter with MockitoSugar with PlayJsonSupport {
  var repository: UserRepository = _
  val shoppingAppActorSystem: ActorSystem = ActorSystem("shoppingApp")
  val shoppingAppExecutor: ExecutionContext = shoppingAppActorSystem.dispatcher

  before {
    repository = mock[UserRepository]
  }

  test("should return 200 with users when repo return successful response") {
    val userRoutes = new UsersRoute(repository)
    when(repository.getUsers).thenReturn(successful(Right(Seq(User("pooja", 24)))))

    Get("/users") ~> userRoutes.routes ~> check {
      status shouldEqual StatusCodes.OK
      responseAs[Seq[User]] shouldEqual Seq(User("pooja", 24))
    }
  }

}

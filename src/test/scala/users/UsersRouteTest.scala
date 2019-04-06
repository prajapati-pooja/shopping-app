package users

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

import scala.concurrent.ExecutionContext
import scala.concurrent.Future.successful


class UsersRouteTest extends FunSuite with Matchers with ScalatestRouteTest with BeforeAndAfter with MockitoSugar with PlayJsonSupport {
  var service: UserService = _
  var userRoutes: UsersRoute = _
  val shoppingAppActorSystem: ActorSystem = ActorSystem("shoppingApp")
  val shoppingAppExecutor: ExecutionContext = shoppingAppActorSystem.dispatcher

  before {
    service = mock[UserService]
    userRoutes = new UsersRoute(service)

  }

  test("should return 200 with users when repo return successful response") {
    val user = User("pooja", 25, "jewoi@example.com", "1278993065", "some random address", List("pizza"))
    when(service.getUsers(any())).thenReturn(successful(Right(Seq(user))))

    Get("/users") ~> userRoutes.routes ~> check {
      status shouldEqual StatusCodes.OK
      responseAs[Seq[User]] shouldEqual Seq(user)
    }
  }

  test("should return users of age 24") {
    val userOfAge24 = User("pooja", 24, "jewoi@example.com", "1278993065", "some random address", List("pizza"))
    when(service.getUsers(Some(24))).thenReturn(successful(Right(Seq(userOfAge24))))

    Get("/users?age=24") ~> userRoutes.routes ~> check {
      status shouldEqual StatusCodes.OK
      responseAs[Seq[User]] shouldEqual Seq(userOfAge24)
    }
  }

}

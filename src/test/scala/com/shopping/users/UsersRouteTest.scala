package com.shopping.users

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit._
import com.shopping.BaseTest
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import scala.concurrent.ExecutionContext
import scala.concurrent.Future.successful

class UsersRouteTest
    extends BaseTest
    with ScalatestRouteTest
    with PlayJsonSupport {
  var service: UserService = _
  var userRoutes: UsersRoute = _
  val shoppingAppActorSystem: ActorSystem = ActorSystem("shoppingApp")
  val shoppingAppExecutor: ExecutionContext = shoppingAppActorSystem.dispatcher

  before {
    service = mock[UserService]
    userRoutes = new UsersRoute(service)

  }

  test("should return all users when age have not been passed to service") {
    val user = User("pooja",
                    25,
                    "jewoi@example.com",
                    "1278993065",
                    "some random address",
                    List("pizza"))
    when(service.getUsers(UserParams(None, None, None)))
      .thenReturn(successful(Right(Seq(user))))

    Get("/users") ~> userRoutes.routes ~> check {
      status shouldEqual StatusCodes.OK
      responseAs[Seq[User]] shouldEqual Seq(user)
    }
  }

  test("should return users of age 24 when age have been passed to service") {
    val userOfAge24 = User("pooja",
                           24,
                           "jewoi@example.com",
                           "1278993065",
                           "some random address",
                           List("pizza"))
    when(service.getUsers(UserParams(Some(24), None, None)))
      .thenReturn(successful(Right(Seq(userOfAge24))))

    Get("/users?age=24") ~> userRoutes.routes ~> check {
      status shouldEqual StatusCodes.OK
      responseAs[Seq[User]] shouldEqual Seq(userOfAge24)
    }
  }

  test("should sort users in descending order based on their age") {
    val user1 = User("pooja",
                     24,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    val user2 = User("pooja",
                     25,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    when(service.getUsers(UserParams(Some(24), Some("AGE"), Some("DESC"))))
      .thenReturn(successful(Right(Seq(user2, user1))))

    Get("/users?age=24&sortBy=age&sortOrder=desc") ~> userRoutes.routes ~> check {
      status shouldEqual StatusCodes.OK
      responseAs[Seq[User]] shouldEqual Seq(user2, user1)
    }
  }

}

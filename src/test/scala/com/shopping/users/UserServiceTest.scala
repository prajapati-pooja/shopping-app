package com.shopping.users

import java.util.concurrent.TimeUnit

import com.shopping.BaseTest
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import scala.concurrent.Await
import scala.concurrent.Future.successful
import scala.concurrent.duration.Duration

class UserServiceTest extends BaseTest with PlayJsonSupport {
  private var repository: UserRepository = _
  private var service: UserService = _

  before {
    repository = mock[UserRepository]
    service = new UserService(repository)
  }

  test("should return com.shopping.users of age 24") {
    val user1 = User("pooja", 25, "jewoi@example.com", "1278993065", "some random address", List("pizza"))
    val user2 = User("pooja", 24, "jewoi@example.com", "1278993065", "some random address", List("pizza"))
    when(repository.getUsers).thenReturn(successful(Right(Seq(user1, user2))))

    val mayBeUsers = Await.result(service.getUsers(Some(24)), Duration(3, TimeUnit.SECONDS))

    mayBeUsers.right.get should contain (user2)
  }

  test("should return all com.shopping.users") {
    val user1 = User("pooja", 25, "jewoi@example.com", "1278993065", "some random address", List("pizza"))
    val user2 = User("pooja", 24, "jewoi@example.com", "1278993065", "some random address", List("pizza"))
    when(repository.getUsers).thenReturn(successful(Right(Seq(user1, user2))))

    val mayBeUsers = Await.result(service.getUsers(None), Duration(3, TimeUnit.SECONDS))

    mayBeUsers.right.get shouldEqual  Seq(user1, user2)
  }

}

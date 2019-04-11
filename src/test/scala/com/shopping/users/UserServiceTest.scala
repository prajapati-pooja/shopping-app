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

  test("should return users of age 24") {
    val user1 = User("pooja",
                     25,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    val user2 = User("pooja",
                     24,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    when(repository.getUsers).thenReturn(successful(Right(Seq(user1, user2))))

    val mayBeUsers =
      Await.result(service.getUsers(UserParams(Some(24), None, None)),
                   Duration(3, TimeUnit.SECONDS))

    mayBeUsers.right.get should contain(user2)
  }

  test("should return all users") {
    val user1 = User("pooja",
                     25,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    val user2 = User("pooja",
                     24,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    when(repository.getUsers).thenReturn(successful(Right(Seq(user1, user2))))

    val mayBeUsers =
      Await.result(service.getUsers(UserParams(None, None, None)),
                   Duration(3, TimeUnit.SECONDS))

    mayBeUsers.right.get shouldEqual Seq(user1, user2)
  }

  test("should sort users in descending order based on their age") {
    val user1 = User("pooja",
                     25,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    val user2 = User("pooja",
                     24,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    when(repository.getUsers).thenReturn(successful(Right(Seq(user2, user1))))

    val mayBeUsers =
      Await.result(
        service.getUsers(UserParams(None, Some("AGE"), Some("DESC"))),
        Duration(3, TimeUnit.SECONDS))

    mayBeUsers.right.get shouldEqual Seq(user1, user2)
  }

  test("should sort users in ascending order based on their age") {
    val user1 = User("pooja",
                     25,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    val user2 = User("pooja",
                     24,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    when(repository.getUsers).thenReturn(successful(Right(Seq(user1, user2))))

    val mayBeUsers =
      Await.result(service.getUsers(UserParams(None, Some("AGE"), Some("ASC"))),
                   Duration(3, TimeUnit.SECONDS))

    mayBeUsers.right.get shouldEqual Seq(user2, user1)
  }

  test("should by default sort users in ascending order based on their age") {
    val user1 = User("pooja",
                     25,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    val user2 = User("pooja",
                     24,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza"))
    when(repository.getUsers).thenReturn(successful(Right(Seq(user1, user2))))

    val mayBeUsers =
      Await.result(service.getUsers(UserParams(None, Some("AGE"), None)),
                   Duration(3, TimeUnit.SECONDS))

    mayBeUsers.right.get shouldEqual Seq(user2, user1)
  }

  test("get total orders of all the users") {
    val user1 = User("pooja",
                     25,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pizza", "Burgor"))
    val user2 = User("pooja",
                     24,
                     "jewoi@example.com",
                     "1278993065",
                     "some random address",
                     List("pasta", "Maggie"))
    when(repository.getUsers).thenReturn(successful(Right(Seq(user1, user2))))

    val mayBeUsers =
      Await.result(service.getTotalOrders,
                   Duration(3, TimeUnit.SECONDS))

    mayBeUsers.right.get shouldEqual Seq("pizza", "Burgor", "pasta", "Maggie")
  }

}

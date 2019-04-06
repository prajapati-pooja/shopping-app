package com.shopping.users

import java.util.concurrent.TimeUnit

import com.shopping.BaseTest
import com.shopping.commons.db.DbClient
import com.shopping.commons.models.ErrorBody
import org.mockito.Mockito
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class UserRepositoryTest extends BaseTest {
  var dbClient: DbClient = _
  var userRepository: UserRepository = _

  before {
    dbClient = mock[DbClient]
    userRepository = new UserRepository(dbClient)
  }

  test("should return list of com.shopping.users") {
    val json = Json.obj("name" -> "pooja",
                        "age" -> 25,
                        "email" -> "jewoi@example.com",
                        "phoneNumber" -> "1278993065",
                        "address" -> "some random address",
                        "order" -> List("pizza"))

    when(dbClient.getUserCollection)
      .thenReturn(Future.successful(Seq(json.as[JsValue])))

    val eventualUsers = userRepository.getUsers

    val users: Either[ErrorBody, Seq[User]] =
      Await.result(eventualUsers, Duration(3, TimeUnit.SECONDS))
    users.right.get.head shouldEqual User("pooja",
                                          25,
                                          "jewoi@example.com",
                                          "1278993065",
                                          "some random address",
                                          List("pizza"))
  }

  test("should not return list of com.shopping.users in case of parsing error") {
    val json = Json.obj("name" -> "pooja")
    when(dbClient.getUserCollection)
      .thenReturn(Future.successful(Seq(json.as[JsValue])))

    val eventualUsers = userRepository.getUsers

    val users: Either[ErrorBody, Seq[User]] =
      Await.result(eventualUsers, Duration(120, TimeUnit.SECONDS))
    users.left.get shouldEqual ErrorBody("parsing error", "JsError.get")
  }

  test("should insert com.shopping.users") {
    userRepository.insertUsers(userList)
    Mockito.verify(dbClient).insertUser(_)
  }

  def userList: List[User] = {
    List(
      User("pooja",
           25,
           "jewoi@example.com",
           "1278993065",
           "some random address1",
           List("pizza")),
      User("Anuska",
           26,
           "jew@example.com",
           "1278993895",
           "some random address2",
           List("Burgur")),
      User("Aiswarya",
           23,
           "j=abwoi@example.com",
           "1278998965",
           "some random address3",
           List("Maggie")),
      User("Alia",
           22,
           "abc@example.com",
           "1908993065",
           "some random address4",
           List("")),
      User("Sonam",
           26,
           "jewoi@example.com",
           "0978993065",
           "some random address5",
           List("Noddle")),
      User("Deepika",
           24,
           "jewoi@example.com",
           "1278993065",
           "some random address",
           List("pizza")),
      User("Anusha",
           31,
           "jewer@example.com",
           "1278093065",
           "some random address",
           List("chacolates chips")),
      User("Priyanka",
           25,
           "jewai@example.com",
           "1278933065",
           "some random address8",
           List("Momos"))
    )
  }

}

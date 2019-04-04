package users

import java.util.concurrent.TimeUnit

import commons.db.DbClient
import commons.models.ErrorBody
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class UserRepositoryTest extends FunSuite with Matchers with BeforeAndAfter with MockitoSugar {
  var dbClient: DbClient = _
  var userRepository: UserRepository = _

  before {
    dbClient = mock[DbClient]
    userRepository = new UserRepository(dbClient)
  }

  test("should return list of users") {
    val json = Json.obj("name" ->  "pooja", "age" -> 25)
    when(dbClient.getUserCollection).thenReturn(Future.successful(Seq(json.as[JsValue])))

    val eventualUsers = userRepository.getUsers

    val users: Either[ErrorBody, Seq[User]] = Await.result(eventualUsers, Duration(120, TimeUnit.SECONDS))
    users.right.get.head shouldEqual User("pooja", 25)
  }

  test("should not return list of users in case of parsing error") {
    val json = Json.obj("name" ->  "pooja")
    when(dbClient.getUserCollection).thenReturn(Future.successful(Seq(json.as[JsValue])))

    val eventualUsers = userRepository.getUsers

    val users: Either[ErrorBody, Seq[User]] = Await.result(eventualUsers, Duration(120, TimeUnit.SECONDS))
    users.left.get shouldEqual ErrorBody("parsing error","JsError.get")
  }
}
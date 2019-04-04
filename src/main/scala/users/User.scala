package users

import play.api.libs.json.{Json, OFormat}

case class User(name: String, age: Int) {
}
object User {
  implicit val userFormat: OFormat[User] = Json.format[User]
}

package com.shopping.users

import play.api.libs.json.{Json, OFormat}

case class User(name: String,
                age: Int,
                email: String,
                phoneNumber: String,
                address: String,
                order: List[String]
               ) {
}

object User {
  implicit val userFormat: OFormat[User] = Json.format[User]
}

package commons.models

import play.api.libs.json.{Json, OFormat}

case class ErrorBody(title: String, message: String)

object ErrorBody {
  implicit val errorBodyFormat: OFormat[ErrorBody] = Json.format[ErrorBody]
}

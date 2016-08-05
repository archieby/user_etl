package by.arc.model

import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class User(
                 id: Option[Long] = None,
                 male: Boolean,
                 nmTitle: String,
                 nmFirst: String,
                 nmLast: String,
                 location: Location,
                 email: String,
                 phone: String
               ) {

  override def toString: String = Json.stringify(Json.toJson(this))
}

object User {
  private val genderFormat = new Format[Boolean] {
    def reads(json: JsValue) = json match {
      case JsString(s) => JsSuccess(s.equalsIgnoreCase("male"))
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("male/female string expected"))))
    }

    override def writes(male: Boolean): JsValue = if (male) JsString("male") else JsString("female")
  }

  implicit val userFormat: Format[User] = (
    (JsPath \ "uid").formatNullable[Long] and
      (JsPath \ "gender").format(genderFormat) and
      (JsPath \ "name" \ "title").format[String] and
      (JsPath \ "name" \ "first").format[String] and
      (JsPath \ "name" \ "last").format[String] and
      (JsPath \ "location").format[Location] and
      (JsPath \ "email").format[String] and
      (JsPath \ "phone").format[String]
    ) (User.apply _, unlift(User.unapply))
}

package by.arc.model

import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class User(
                 //                 id: Long = -1,
                 male: Boolean,
                 nmTitle: String,
                 nmFirst: String,
                 nmLast: String,
                 location: Location,
                 email: String,
                 phone: String
               )

object User {
  private val genderReads = new Reads[Boolean] {
    def reads(json: JsValue) = json match {
      case JsString(s) => JsSuccess(s.equalsIgnoreCase("male"))
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("male/female string expected"))))
    }
  }

  implicit val locationReads: Reads[User] = (
    (JsPath \ "gender").read(genderReads) and
      (JsPath \ "name" \ "title").read[String] and
      (JsPath \ "name" \ "first").read[String] and
      (JsPath \ "name" \ "last").read[String] and
      (JsPath \ "location").read[Location] and
      (JsPath \ "email").read[String] and
      (JsPath \ "phone").read[String]
    ) (User.apply _)
}

package by.arc.model

import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Location(
                     street: String,
                     city: String,
                     state: String,
                     postcode: String
                   )

object Location {
  private val pcReads = new Reads[String] {
    def reads(json: JsValue) = json match {
      case JsString(s) => JsSuccess(s)
      case JsNumber(n) => JsSuccess(n.toString())
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("male/female string expected"))))
    }
  }

  implicit val locationFormat: Format[Location] = (
    (JsPath \ "street").format[String] and
      (JsPath \ "city").format[String] and
      (JsPath \ "state").format[String] and
      (JsPath \ "postcode").format(pcReads)
    ) (Location.apply , unlift(Location.unapply))
}
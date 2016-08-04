package by.arc.model

import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.functional.syntax._

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

  implicit val locationReads: Reads[Location] = (
    (JsPath \ "street").read[String] and
      (JsPath \ "city").read[String] and
      (JsPath \ "state").read[String] and
      (JsPath \ "postcode").read(pcReads)
    ) (Location.apply _)
}
package by.arc.service

import by.arc._
import by.arc.http.WsClient._
import by.arc.model.User
import play.api.libs.json.{JsPath, Json}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class WsUserProducer extends UserProducer {
  override def createUsers(num: Int = 0): Seq[User] = {
    val cnt = if (num > 0) num else conf.getInt("http.default.results")
    import scala.concurrent.ExecutionContext.Implicits.global

    val popFuture = withClient(conf.getString("http.users.url") + cnt) { resp =>
      Success(Json.parse(resp).as((JsPath \ "results").read[Seq[User]]))
    }.recover {
      case e: Throwable => Failure(e)
    }

    Await.result(popFuture, 10 seconds) match {
      case Success(users) => users
      case Failure(e) => throw e
    }
  }
}

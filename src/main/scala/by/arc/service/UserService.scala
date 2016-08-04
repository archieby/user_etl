package by.arc.service

import java.util.ResourceBundle

import by.arc._
import by.arc.dao.UserDao
import by.arc.http.WsClient._
import by.arc.model.User
import play.api.libs.json.{JsPath, Json}

import scala.concurrent.Future

class UserService(val userDao: UserDao) {

  def populateUsers(num: Int = conf.getInt("http.default.results")): Future[String] = {
    withClient(conf.getString("http.users.url")) { resp =>
      val usrs = Json.parse(resp).as((JsPath \ "results").read[Seq[User]])
      userDao.saveAll(usrs)
      s"$num successfully added to DB."
    }
  }

  def cleanUsers() = userDao.clean()

  //  def cleanUsers() = userDao.clean()

}

object UserService {
  private val myBundle: ResourceBundle = ResourceBundle.getBundle("com.intellij.fontChooser.FontChooser")
}
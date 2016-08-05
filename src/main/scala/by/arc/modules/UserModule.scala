package by.arc.modules

import by.arc.dao.{ScalikeJdbcUserDao, UserDao}
import by.arc.service.{UserProducer, UserService, WsUserProducer}
import com.softwaremill.macwire._

trait UserModule {
  lazy val userDao: UserDao = wire[ScalikeJdbcUserDao]

  lazy val userProducer: UserProducer = wire[WsUserProducer]

  lazy val userService: UserService = wire[UserService]
}

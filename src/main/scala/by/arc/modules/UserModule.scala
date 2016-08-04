package by.arc.modules

import by.arc.dao.{ScalikeJdbcUserDao, UserDao}
import by.arc.service.UserService
import com.softwaremill.macwire._

trait UserModule {
  lazy val userDao: UserDao = wire[ScalikeJdbcUserDao]

  lazy val userService: UserService = wire[UserService]
}

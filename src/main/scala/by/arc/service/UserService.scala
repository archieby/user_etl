package by.arc.service

import by.arc._
import by.arc.dao.UserDao
import by.arc.service.UserService._

class UserService(val userDao: UserDao, val userProducer: UserProducer) {

  def initDb(): String = handle(userDao.initDb())("Unable to initialize DB") { b =>
    s"Database Initialized."
  }

  def populateUsers(num: Int = 0): String = handle {
    val users = userProducer.createUsers(num)
    userDao.saveAll(users).size
  }("Unable to populate Users")(i => s"$i Users successfully added to the DB.")

  def count: String = handle(userDao.count())("Unable to count Users")(i => s"There are $i Users in the DB.")

  def addUser(firstName: String, lastName: String) = handle {
    val users = userProducer.createUsers(1)
    val newLocation = users.head.location.copy()
    val newUser = users.head.copy(nmFirst = firstName, nmLast = lastName, location = newLocation)
    (userDao.save(newUser), firstName, lastName)
  }(s"Unable to add User['$firstName'/'$lastName']")(d => s"New User was added to DB [${d._1}/'${d._2}'/'${d._3}']")

  def cleanUsers() = handle(userDao.clean())("DB cleanup Failed")(i => s"Data for $i Users successfully removed from the DB.")

  def getAllUsers() = handle(userDao.getAll())("Unable to retrieve all Users")(usrs => s"All Users:$LS${stringifyUsers(usrs)}")

  def getByName(name: String) = handle(userDao.getByName(name))("Unable to retrieve the User by Name") { usrs =>
    s"Found by Name:$LS${stringifyUsers(usrs)}"
  }

  def getById(id: Long) = handle(userDao.getById(id))("Unable to retrieve the User by Id") { usr =>
    usr.fold(s"No User exists for id:$id.")(u => s"User Found by Id: $u.")
  }
}

object UserService {
  def handle[T](task: => T)(fail: String)(succ: T => String): String = {
    try {
      succ(task)
    } catch {
      case e: Throwable => fail + s":$LS" + (if (e.getMessage == null) e.getClass.getSimpleName else e.getMessage)
    }
  }
}
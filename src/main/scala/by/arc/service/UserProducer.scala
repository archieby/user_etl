package by.arc.service

import by.arc.model.User

trait UserProducer {
  def createUsers(num: Int = 0): Seq[User]
}

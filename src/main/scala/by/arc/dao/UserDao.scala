package by.arc.dao

import by.arc.model.User

trait UserDao {
  def getAll(): Seq[User]

  def saveAll(seq: Seq[User]): Seq[Long]

  def save(user: User): Long

  def getById(id: Long): Option[User]

  def getByName(firstNm: String, lastNm: String): Option[User]

  def clean(): Int
}

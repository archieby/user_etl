package by.arc.dao

import by.arc.model.User

trait UserDao {
  def getAll(): Seq[User]

  def saveAll(seq: Seq[User]): Seq[Long]

  def save(user: User): Long

  def getById(id: Long): Option[User]

  def getByName(name: String): Seq[User]

  def count(): Int

  def clean(): Int

  def initDb(): Boolean
}

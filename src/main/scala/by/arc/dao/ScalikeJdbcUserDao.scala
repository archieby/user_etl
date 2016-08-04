package by.arc.dao

import by.arc.model.{Location, User}
import scalikejdbc.{DB, _}
import scalikejdbc.config.DBs

class ScalikeJdbcUserDao extends UserDao {
  DBs.setupAll()

  override def getAll(): Seq[User] = DB readOnly { implicit session =>
    sql"${UserSql}".map(userMapping).list().apply()
  }

  override def saveAll(usrs: Seq[User]): Seq[Long] = DB localTx { implicit session =>
    val keys = sql"INSERT INTO users (male, nm_title, nm_first, nm_last, email, phone) VALUES (?, ?, ?, ?, ?, ?)"
      .batchAndReturnGeneratedKey("id", userInsertData(usrs): _*).apply().asInstanceOf[Seq[Long]]


    sql"INSERT INTO locations (user_id, street, city, state, postcode) VALUES (?, ?, ?, ?, ?)"
      .batch(locInsertData(usrs.map(_.location), keys): _*).apply()

    keys
  }

  override def save(user: User): Long = DB localTx { implicit session =>
    sql"""INSERT INTO users (male, nm_title, nm_first, nm_last, email, phone) VALUES (
          |${user.male},
          |${user.nmTitle},
          |${user.nmFirst},
          |${user.nmLast},
          |${user.email},
          |${user.phone})""".stripMargin
      .updateAndReturnGeneratedKey("id").apply()
  }

  override def getById(id: Long): Option[User] = DB readOnly { implicit session =>
    sql"${UserSql} where u.id = ${id}"
      .map(userMapping).single.apply()
  }

  override def getByName(firstNm: String, lastNm: String): Option[User] = DB readOnly { implicit session =>
    sql"${UserSql} where u.nm_first = ${firstNm} and u.nm_last = ${lastNm}"
      .map(userMapping).single.apply()
  }


  override def clean(): Int = DB localTx { implicit session =>
    sql"delete from locations".update().apply()
    sql"delete from users".update().apply()
  }


  private val UserSql =
    sqls"""SELECT
           |    u.male,
           |    u.nm_title,
           |    u.nm_first,
           |    u.nm_last,
           |    u.email,
           |    u.phone,
           |    l.street,
           |    l.city,
           |    l.state,
           |    l.postcode
           |FROM
           |    users u
           |LEFT JOIN
           |    locations l
           |ON
           |    u.id = l.user_id""".stripMargin

  private def userMapping(rs: WrappedResultSet): User = {
    val location = Location(rs.string("street"), rs.string("city"), rs.string("state"), rs.string("postcode"))
    User(rs.boolean("male"), rs.string("nm_title"), rs.string("nm_first"), rs.string("nm_last"), location, rs.string("email"), rs.string("phone"))
  }

  private def userInsertData(users: Seq[User]) = users.map(u => Seq(
    u.male,
    u.nmTitle,
    u.nmFirst,
    u.nmLast,
    u.email,
    u.phone))

  private def locInsertData(locs: Seq[Location], keys: Seq[Long]) = locs.zip(keys).map { case (l, k) =>
    Seq(
      k,
      l.street,
      l.city,
      l.state,
      l.postcode)
  }
}

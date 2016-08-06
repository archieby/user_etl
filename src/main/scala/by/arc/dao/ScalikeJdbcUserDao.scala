package by.arc.dao

import by.arc._
import by.arc.model.{Location, User}
import scalikejdbc.config.DBs
import scalikejdbc.{DB, _}

class ScalikeJdbcUserDao extends UserDao {
  DBs.setupAll()

  override def initDb(): Boolean = DB localTx { implicit session =>
    SQL(readFile(conf.getString("db.default.users.schema"))).execute.apply()
    SQL(readFile(conf.getString("db.default.locations.schema"))).execute.apply()
    true
  }

  override def count(): Int = DB readOnly { implicit session =>
    sql"SELECT COUNT(*) as cnt FROM users".map(_.int("cnt")).single().apply().get
  }

  override def getAll(): Seq[User] = DB readOnly { implicit session =>
    sql"${UserSql}".map(userMapping).list().apply()
  }

  override def saveAll(usrs: Seq[User]): Seq[Long] = DB localTx { implicit session =>
    val keys = sql"INSERT INTO users (male, nm_title, nm_first, nm_last, email, phone) VALUES (?, ?, ?, ?, ?, ?)"
      .batchAndReturnGeneratedKey(userInsertData(usrs): _*).apply().asInstanceOf[Seq[Long]]


    sql"INSERT INTO locations (user_id, street, city, state, postcode) VALUES (?, ?, ?, ?, ?)"
      .batch(locInsertData(usrs.map(_.location), keys): _*).apply()

    keys
  }

  override def save(user: User): Long = DB localTx { implicit session =>
    val id =
      sql"""INSERT INTO users (male, nm_title, nm_first, nm_last, email, phone) VALUES (
            |${user.male},
            |${user.nmTitle},
            |${user.nmFirst},
            |${user.nmLast},
            |${user.email},
            |${user.phone})""".stripMargin
        .updateAndReturnGeneratedKey.apply()

    val loc = user.location
    sql"""INSERT INTO locations (user_id, street, city, state, postcode) VALUES (
          |${id},
          |${loc.street},
          |${loc.city},
          |${loc.state},
          |${loc.postcode})""".stripMargin
      .update().apply()
    id
  }

  override def getById(id: Long): Option[User] = DB readOnly { implicit session =>
    sql"${UserSql} where u.id = ${id}"
      .map(userMapping).single.apply()
  }

  override def getByName(nm: String): Seq[User] = DB readOnly { implicit session =>
    val name = s"%${nm.toUpperCase()}%"
    sql"${UserSql} where upper(u.nm_first) like ${name} or upper(u.nm_last) like ${name}"
      .map(userMapping).list.apply()
  }


  override def clean(): Int = DB localTx { implicit session =>
    sql"delete from locations".update().apply()
    sql"delete from users".update().apply()
  }


  private val UserSql =
    sqls"""SELECT
           |    u.id,
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
    User(rs.longOpt("id"), rs.boolean("male"), rs.string("nm_title"), rs.string("nm_first"), rs.string("nm_last"), location, rs.string("email"), rs.string("phone"))
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



package by.arc.dao

import by.arc._
import by.arc.UnitSpec
import by.arc.model.{Location, User}
import by.arc.service.UserProducer
import org.scalatest.BeforeAndAfter
import scalikejdbc.config.DBs
import scalikejdbc.{DB, SQL}

class ScalikeJdbcUserDaoSpec extends UnitSpec with BeforeAndAfter {

  DBs.setupAll()
  userDao.initDb()

  after {
    userDao.clean()
  }

  val usrs5 = userProducer.createUsers(5)
  val usrs20 = userProducer.createUsers(20)

  "DB" should "have 0 Users when DB is empty" in {
    val cnt = userDao.count()

    assert(cnt === 0)
  }

  it should "have 5 correct users after 5 users are added, all reachable by id" in {
    val ids = userDao.saveAll(usrs5)

    assert(userDao.count() === 5)
    ids.foreach { id =>
      assert(userDao.getById(id).isDefined)
    }

    userDao.saveAll(usrs20)
    assert(userDao.count() === 25)
  }

  it should "get additional User reachable by name after save()" in {
    val user = usrs5.head
    val newLocation = user.location.copy()
    val newUser = user.copy(nmFirst = "xxxvasilxxx", nmLast = "xxxpupkinxxx", location = newLocation)
    val ids = userDao.save(newUser)

    assert(userDao.count() === 1)
    assert(userDao.getByName("xxxvasilxxx").size == 1)
    assert(userDao.getByName("xxxpupkinxxx").size == 1)
  }

  it should "be cleaned after insertion" in {
    userDao.saveAll(usrs5)

    assert(userDao.clean() === 5)
    assert(userDao.count() == 0)
  }

  it should "return everything populated by getAll()" in {
    val keys = userDao.saveAll(usrs20)

    val usrs = userDao.getAll()

    assert(userDao.count() == 20)
    assert(usrs.size == 20)
    usrs.foreach { u =>
      assertUsrPopulated(u)
      assert(userDao.getById(u.id.get).isDefined)
    }
  }

  override lazy val userProducer: UserProducer = new UserProducer {
    private val Usr = User(None, true, "mr", "arc", "by", Location("asd", "sdf", "dfg", "222"), "aaa@aa.by", "55555")

    override def createUsers(num: Int): Seq[User] = {
      val rv = Seq.fill(num)(Usr).zipWithIndex.map { case (u, i) =>
        u.copy(nmFirst = u.nmFirst + i)
      }
      rv
    }
  }
}

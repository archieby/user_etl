package by.arc.service

import by.arc.{UnitSpec, _}

class WsUserProducerSpec extends UnitSpec {
  "A Producer" should "return the requested number of Users" in {
    val us1 = userProducer.createUsers(5)
    val us2 = userProducer.createUsers(10)
    val us3 = userProducer.createUsers(100)

    assert(us1.size === 5)
    assert(us2.size === 10)
    assert(us3.size === 100)
  }

  it should "produce default number of users if invoked without arguments" in {
    val us1 = userProducer.createUsers()
    val us2 = userProducer.createUsers()

    val defUsers = conf.getInt("http.default.results")
    assert(us1.size === defUsers)
    assert(us2.size === defUsers)
  }

  it should "produce fully populated Users" in {
    val us1 = userProducer.createUsers(100)

    us1.foreach(assertUsrPopulated)
  }
}

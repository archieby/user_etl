package by.arc

import by.arc.model.{Location, User}
import by.arc.modules.UserModule
import org.scalatest._

import scala.io.Source._

abstract class UnitSpec extends FlatSpec with Matchers with OptionValues with Inside with Inspectors with UserModule {
  protected def assertUsrPopulated(u: User) = {
    assert(u !== null)

    assert(u.male !== null)
    assert(u.nmTitle !== null)
    assert(u.nmFirst !== null)
    assert(u.nmLast !== null)
    assert(u.email !== null)
    assert(u.phone !== null)

    assertLocPopulated(u.location)
  }

  protected def assertLocPopulated(l: Location) = {
    assert(l !== null)

    assert(l.street !== null)
    assert(l.city !== null)
    assert(l.state !== null)
    assert(l.postcode !== null)
  }
}
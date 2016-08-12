package by.arc.main

import by.arc._
import by.arc.modules.UserModule

import scala.io.StdIn._

object Main extends App with UserModule {
  val InitDbRgxp = """^initdb$""".r
  val PopulateRgxp = """^populate(?:\s+(\d+))?$""".r
  val AddRgxp = """^add\s+(\w+)\s+(\w+)$""".r
  val CleanRgxp = """^clean$""".r
  val AllRgxp = """^all$""".r
  val ByIdRgxp = """^get\s+(\d+)$""".r
  val ByNameRgxp = """^get\s+(\w+)$""".r
  val CountRgxp = """^count$""".r
  val HelpRgxp = """^help$""".r
  val ExitRgxp = """^exit$""".r

  lazy val usage =
    s"""\tinitdb - Initializes DB ([re]creates required tables).
        |\tpopulate [num] - populates DB with num Users. If num not provided default value ${conf.getInt("http.default.results")} is used.
        |\tadd <first-name> <last-name> - adds User with the provided name to the DB.
        |\tclean - cleans DB.
        |\tall - retrieves all User data from DB.
        |\tget <id-numeric> - gets the User from DB by id if one exists.
        |\tget <name-string> - gets all Users from DB whoes first or last name matches ignore case this wildcard - *<name-string>*.
        |\tcount - returns number of Users in DB.
        |\thelp - shows this help.
        |\texit - exits from the Application.""".stripMargin

  println(s"Application usage:$LS$usage")
  while (true) {
    val tokens = readLine().trim
    println(tokens match {
      case InitDbRgxp() => userService.initDb()
      case PopulateRgxp(null) => userService.populateUsers()
      case PopulateRgxp(n) => userService.populateUsers(n.toInt)
      case AddRgxp(fn, ln) => userService.addUser(fn, ln)
      case CleanRgxp() => userService.cleanUsers()
      case AllRgxp() => userService.getAllUsers()
      case ByIdRgxp(id) => userService.getById(id.toLong)
      case ByNameRgxp(name) => userService.getByName(name)
      case CountRgxp() => userService.count
      case HelpRgxp() => s"Available commands:$LS$usage"
      case ExitRgxp() => System.exit(0)
      case s => s"'$s' is an invalid input. See the list of available commands:$LS$usage"
    })
  }
}

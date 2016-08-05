package by

import by.arc.model.User
import com.typesafe.config.ConfigFactory

import scala.io.Source._

/**
  * Created by archie on 8/1/2016.
  */
package object arc {
  // Load our own config values from the default location, application.conf
  val conf = ConfigFactory.load()

  val LS = System.getProperty("line.separator")

  def readFile(fn: String): String = {
    fromInputStream(getClass.getResourceAsStream(fn)).mkString
  }

  def stringifyUsers(us: Seq[User]): String = if (us.nonEmpty) us.map(_.toString).mkString(LS) else "<none>"
}

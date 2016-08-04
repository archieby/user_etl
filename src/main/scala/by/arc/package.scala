package by

import com.typesafe.config.ConfigFactory

/**
  * Created by archie on 8/1/2016.
  */
package object arc {
  // Load our own config values from the default location, application.conf
  val conf = ConfigFactory.load()
}

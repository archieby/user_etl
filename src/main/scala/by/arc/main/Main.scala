package by.arc.main

import by.arc.modules.UserModule
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App with UserModule {
  userService.populateUsers().foreach(println)
}

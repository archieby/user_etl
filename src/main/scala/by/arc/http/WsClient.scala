package by.arc.http


import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import by.arc._
import by.arc.model.User
import by.arc.modules.UserModule
import play.api.libs.json.{JsPath, Json}
import play.api.libs.ws.ahc.{AhcWSClient, AhcWSClientConfig}

import scala.concurrent.Future

trait WsClient {
  def withClient[T](url: String)(block: String => T): Future[T] = {
    import scala.concurrent.ExecutionContext.Implicits.global

    val name = "ws-test-client-" + WsClient.instanceNumber.getAndIncrement
    val system = ActorSystem(name)
    val materializer = ActorMaterializer(namePrefix = Some(name))(system)

    val client = AhcWSClient(AhcWSClientConfig(maxRequestRetry = conf.getInt("http.retry")))(materializer)

    val request = client.url(url).withHeaders("Accept" -> "application/json").get()
    //    request.

    val rv = request.map(resp =>block(resp.body))

    rv.onComplete(_ => {
      println("!!!CLOSING!!!")
      client.close()
    })

    rv
  }
}

object WsClient extends WsClient {

  import java.util.concurrent.atomic.AtomicInteger

  // This is used to create fresh names when creating `ActorMaterializer` instances in `WsClient.withClient`.
  // The motivation is that it can be useful for debugging.
  private val instanceNumber = new AtomicInteger(1)
}

object testApp extends App with UserModule {
  WsClient.withClient(conf.getString("http.users.url")) { resp =>
    val usrs = Json.parse(resp).as((JsPath \ "results").read[Seq[User]])

    //        userDao.saveAll(usrs)
    //    println(userDao.save(usrs(0)))
    //println(userDao.getByName("mia", "jenkins"))
  }

  println(userDao.getAll())
}
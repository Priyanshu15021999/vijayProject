package Controlar
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{entity, path, _}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol

import scala.util.{Failure, Success, Try}
import akka.http.scaladsl.model.StatusCodes
import modal.Employee
import modal.Service
trait EmployeeJson extends SprayJsonSupport with DefaultJsonProtocol{
import spray.json
  import spray.json._

  implicit val printar = PrettyPrinter
  implicit val JsonStudetFormat = jsonFormat4(Employee)

}
class FirstControlar  extends EmployeeJson{

  val service = new Service()

  val find = path("Find" / IntNumber) { id =>
    get {
      //val studentFind= Service.findOntStudent(id)
      onSuccess(service.FindEmployee(id)) { donuts =>
        complete(StatusCodes.OK, donuts)
      }
    }
  } ~ path("Insert") {
    post {
      entity(as[Employee]) { employee =>

        val studentFind = service.InsertEmployee(employee)
        onComplete(studentFind) {
          case Success(value) => complete(value)

        }
      }

    }
  } ~ path("Update" / IntNumber) { id =>
    put {
      entity(as[Employee]) { employee =>
        //val studentFind = Service.updateStudent(id, "StudentName", student.sName)
        onSuccess(service.UpdateEmployee(id, "EmployeeName",employee.EmployeeName)) { donuts =>
          complete(StatusCodes.OK, donuts)
        }
      }
    }

  } ~ path("Delete" / IntNumber) { id =>
    delete {
      val studentFind = service.DeleteEmployee(id)
      onSuccess(studentFind) { donuts =>
        complete(StatusCodes.OK, donuts)
      }
    }
  }
}


object Controlar1 {
  def main(args: Array[String]): Unit = {
    implicit val syatem = ActorSystem()
    implicit val materializer= ActorMaterializer()
    implicit val dc= syatem.dispatchers
    val port = 8080
    val host = "localhost"
    val routes= new FirstControlar().find
    val httpRoutesOutput = Http().bindAndHandle(routes, host, port)

  }
}
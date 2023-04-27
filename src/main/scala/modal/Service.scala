package modal

import org.mongodb.scala.bson.{ BsonDocument, BsonDouble}
import org.mongodb.scala.{ MongoClient}
import scala.concurrent.duration.DurationInt
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import org.mongodb.scala.model.{Filters,Updates}
import scala.language.postfixOps


class
Service extends EmployeeServices {
  val URL = "mongodb://localhost:27017"
  val Clint = MongoClient(URL)
  val Database = Clint.getDatabase("EmployeeDatabase")
  val Employee_Collection = Database.getCollection("EmployeeCollection")


  override def InsertEmployee(employee: Employee): Future[String] = Future{
  val document = BsonDocument(
    "_id" -> employee._id,
    "EmployeeName" -> employee.EmployeeName,
    "EmployeeDept" -> employee.EmployeeDept,
    "EmployeeSalary" -> employee.EmployeeSalary
  )
    val s= Employee_Collection.insertOne(document)
    val out= Await.result(s.toFuture(), 100 seconds)
    "Employee Inserted"
  }

  override def FindEmployee(_id: Int): Future[String] = Future{
    val filter = Filters.eq("_id", _id)
    val s = Employee_Collection.find(filter)
    val out = Await.result(s.toFuture(), 100 seconds)
    out.map(a=>a.toJson()).toString()
  }

  override def UpdateEmployee(_id: Int, field: String, value: String): Future[String] = Future{
    val filter = Filters.eq("_id", _id)
    val set =Updates.set( field,value)
    val s = Employee_Collection.updateOne(filter,set)
    val out = Await.result(s.toFuture(), 100 seconds)
    "Employee Updated"
  }

  override def DeleteEmployee(_id: Int): Future[String] = Future{
    val filter = Filters.eq("_id", _id)
    val s = Employee_Collection.deleteOne(filter)
    val out = Await.result(s.toFuture(), 100 seconds)
    "Employee Deleted"
  }
}

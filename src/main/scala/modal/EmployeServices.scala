package modal

import scala.concurrent.Future

trait EmployeeServices {
  def InsertEmployee (employe: Employee):Future[String]
  def FindEmployee (_id: Int):Future[String]
  def UpdateEmployee (_id: Int,field:String,value:String):Future[String]
  def DeleteEmployee (_id: Int):Future[String]


}

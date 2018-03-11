package example.models


case class Order(id: Long, petId: Long, quantity: Int, shipDate: String, status: String, complete: Boolean)

case class Category(id: Long, name: String)

case class User(id: Long, firstName: String, lastName: String, email: String, password: String, phone: String, userStatus: Int)

case class Tag(id: Long, name: String)

case class Pet(id: Long, category: Category, name: String, photoUrls: Array[String], tags: Array[Tag], status: String)


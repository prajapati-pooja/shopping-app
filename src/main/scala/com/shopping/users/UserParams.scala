package com.shopping.users

case class UserParams(age: Option[Int],
                      sortBy: Option[String],
                      sortOrder: Option[String])
object UserParams {
  def create(age: Option[Int],
             sortBy: Option[String],
             sortOrder: Option[String]): UserParams = {
    val maybeSortBy =
      sortBy.map(_.trim).filter(_.length != 0).map(_.toUpperCase)
    val maybeSortOrder =
      sortOrder.map(_.trim).filter(_.length != 0).map(_.toUpperCase)
    UserParams(age, maybeSortBy, maybeSortOrder)
  }
}

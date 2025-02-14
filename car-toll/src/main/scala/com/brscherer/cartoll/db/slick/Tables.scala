package com.brscherer.cartoll.db.slick
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile: slick.jdbc.JdbcProfile = slick.jdbc.PostgresProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for
  // tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Toll.schema

  /** Entity class storing rows of table Toll
   *  @param id Database column id SqlType(int4), PrimaryKey
   *  @param date Database column date SqlType(date)
   *  @param carType Database column car_type SqlType(varchar)
   *  @param amount Database column amount SqlType(int4)
   *  @param licensePlate Database column license_plate SqlType(varchar) */
  case class TollRow(id: Int, date: java.sql.Date, carType: String, amount: Int, licensePlate: String)
  /** GetResult implicit for fetching TollRow objects using plain SQL queries */
  implicit def GetResultTollRow(implicit e0: GR[Int], e1: GR[java.sql.Date], e2: GR[String]): GR[TollRow] = GR{
    prs => import prs._
    (TollRow.apply _).tupled((<<[Int], <<[java.sql.Date], <<[String], <<[Int], <<[String]))
  }
  /** Table description of table toll. Objects of this class serve as prototypes for rows in queries. */
  class Toll(_tableTag: Tag) extends profile.api.Table[TollRow](_tableTag, "toll") {
    def * = ((id, date, carType, amount, licensePlate)).mapTo[TollRow]
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(date), Rep.Some(carType), Rep.Some(amount), Rep.Some(licensePlate))).shaped.<>({r=>import r._; _1.map(_=> (TollRow.apply _).tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(int4), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column date SqlType(date) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("date")
    /** Database column car_type SqlType(varchar) */
    val carType: Rep[String] = column[String]("car_type")
    /** Database column amount SqlType(int4) */
    val amount: Rep[Int] = column[Int]("amount")
    /** Database column license_plate SqlType(varchar) */
    val licensePlate: Rep[String] = column[String]("license_plate")
  }
  /** Collection-like TableQuery object for table Toll */
  lazy val Toll = new TableQuery(tag => new Toll(tag))
}

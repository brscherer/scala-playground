package com.brscherer.cartoll.db.codegen

object DBCodeGenRunner extends App {
  slick.codegen.SourceCodeGenerator.main(
    Array(
      "slick.jdbc.PostgresProfile",
      "org.postgresql.Driver",
      "jdbc:postgresql://localhost:5432/toll",
      "src/main/scala",
      "com.brscherer.cartoll.db.slick",
      "postgres",
      "postgres"
    )
  )
}
package com.brscherer.manga
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication
@EnableJdbcRepositories(basePackages = Array("com.brscherer.manga.repository"))
class MangaTrackerApp

object MangaTrackerApp {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[MangaTrackerApp], args*)
  }
}
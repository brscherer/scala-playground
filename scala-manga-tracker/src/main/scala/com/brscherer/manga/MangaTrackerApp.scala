package com.brscherer.manga
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MangaTrackerApp

object MangaTrackerApp {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[MangaTrackerApp], args*)
  }
}
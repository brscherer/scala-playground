val springBootVersion = "3.5.3"
val scalaVersionUsed = "3.6.3"

ThisBuild / scalaVersion := scalaVersionUsed

lazy val root = (project in file("."))
  .settings(
    name := "scala-manga-tracker",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion excludeAll (
        ExclusionRule("ch.qos.logback", "logback-classic"),
        ExclusionRule("org.apache.logging.log4j", "log4j-to-slf4j")
      ),
      "org.springframework.boot" % "spring-boot-starter-actuator" % springBootVersion,
      "org.springframework.boot" % "spring-boot-starter-security" % springBootVersion,
      "org.springframework.boot" % "spring-boot-starter-jdbc" % springBootVersion,
      "org.springframework.boot" % "spring-boot-starter-data-jdbc" % springBootVersion,
      "org.apache.logging.log4j" % "log4j-core" % "2.24.3",
      "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test,
      "com.h2database" % "h2" % "2.2.224",
    ),
    fork := true
  )

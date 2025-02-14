ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
    name := "car-toll"
  )

libraryDependencies += "org.apache.pekko"   %% "pekko-actor"   % "1.1.2"
libraryDependencies += "org.apache.pekko"   %% "pekko-stream"  % "1.1.2"
libraryDependencies += "org.apache.pekko"   %% "pekko-http"    % "1.1.0"
libraryDependencies += "org.json4s"         %% "json4s-native" % "4.0.7"
libraryDependencies += "org.postgresql"      % "postgresql"    % "42.7.4"
libraryDependencies += "org.slf4j"           % "slf4j-api"     % "2.0.16"
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.5.1",
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.5.1"
)
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.5.1"
name := "scala-3x"
version := "1.0"
scalaVersion := "3.6.1"

libraryDependencies+= "org.scalatest" %% "scalatest" % "3.2.9" % "test"
// https://mvnrepository.com/artifact/org.json4s/json4s-ext
libraryDependencies += "org.json4s" %% "json4s-ext" % "4.0.7"
// https://mvnrepository.com/artifact/org.json4s/json4s-native
libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.7"
// https://mvnrepository.com/artifact/org.apache.pekko/pekko-http
libraryDependencies += "org.apache.pekko" %% "pekko-http" % "1.1.0"
// https://mvnrepository.com/artifact/org.apache.pekko/pekko-actor
libraryDependencies += "org.apache.pekko" %% "pekko-actor" % "1.1.2"
// https://mvnrepository.com/artifact/org.apache.pekko/pekko-stream
libraryDependencies += "org.apache.pekko" %% "pekko-stream" % "1.1.2"
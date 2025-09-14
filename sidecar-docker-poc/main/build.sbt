ThisBuild / scalaVersion     := "3.3.0"

lazy val commonSettings = Seq(
  scalaVersion := "3.3.0",
  version      := "0.1.0-SNAPSHOT",
)

lazy val root = (project in file("."))
  .aggregate(main)
  .settings(commonSettings)

lazy val main = (project in file("main"))
  .settings(
    commonSettings,
    name := "sidecar-main",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.5.1",
      "org.http4s"    %% "http4s-ember-server" % "0.23.30",
      "org.http4s"    %% "http4s-ember-client" % "0.23.30",
      "org.http4s"    %% "http4s-dsl"          % "0.23.30",
      "ch.qos.logback" % "logback-classic" % "1.4.11",
      "org.http4s"    %% "http4s-circe"     % "0.23.30",
      "io.circe"      %% "circe-generic"    % "0.14.6",
      "org.scalameta" %% "munit"               % "0.7.29"            % Test,
      "org.typelevel" %% "munit-cats-effect-3" % "1.0.7"             % Test
    ),
    Compile / run / mainClass := Some("Main"),
    Compile / run / fork := true
  )

// *****************************************************************************
// Build settings
// *****************************************************************************

inThisBuild(
  Seq(
    organization := "rocks.heikoseeberger",
    organizationName := "Heiko Seeberger",
    startYear := Some(2021),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.com/hseeberger/log4scala")),
    developers := List(
      Developer(
        "hseeberger",
        "Heiko Seeberger",
        "mail@heikoseeberger.rocks",
        url("https://github.com/hseeberger")
      )
    ),
    scalaVersion := "3.0.0",
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-encoding",
      "UTF-8",
    ),
    testFrameworks += new TestFramework("munit.Framework"),
    scalafmtOnCompile := false, // TODO set to true once working for Scala 3!
    dynverSeparator := "_",     // the default `+` is not compatible with docker tags
  )
)

// *****************************************************************************
// Projects
// *****************************************************************************

lazy val slf4s =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin)
    .settings(commonSettings)
    .settings(
      libraryDependencies ++= Seq(
        // Logging
        library.slf4jApi,
        // Testing
        library.logback         % Test,
        library.mockitoCore     % Test,
        library.munit           % Test,
        library.munitScalaCheck % Test,
      ),
    )

// *****************************************************************************
// Project settings
// *****************************************************************************

lazy val commonSettings =
  Seq(
    // Also (automatically) format build definition together with sources
    Compile / scalafmt := {
      val _ = (Compile / scalafmtSbt).value
      (Compile / scalafmt).value
    },
  )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {
    object Version {
      val log4j   = "2.14.0"
      val logback = "1.2.3"
      val mockito = "3.7.7"
      val munit   = "0.7.26"
      val slf4j   = "1.7.30"
    }
    // Logging
    val slf4jApi = "org.slf4j" % "slf4j-api" % Version.slf4j
    // Testing
    val logback         = "ch.qos.logback" % "logback-classic"  % Version.logback
    val mockitoCore     = "org.mockito"    % "mockito-core"     % Version.mockito
    val munit           = "org.scalameta" %% "munit"            % Version.munit
    val munitScalaCheck = "org.scalameta" %% "munit-scalacheck" % Version.munit
  }

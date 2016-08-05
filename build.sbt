name := "user_etl"

version := "1.0"

scalaVersion := "2.11.7"

val playVersion = "2.5.+"
val scalikeVersion = "2.4.+"

libraryDependencies += "com.typesafe.play" % "play-ws_2.11" % playVersion
libraryDependencies += "com.typesafe.play" % "play-json_2.11" % playVersion
libraryDependencies += "com.typesafe" % "config" % "1.3.+"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % scalikeVersion,
  "org.scalikejdbc" % "scalikejdbc-config_2.11" % scalikeVersion,
  "org.postgresql" % "postgresql" % "9.4.1209.jre7",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.apache.commons" % "commons-dbcp2" % "2.1.+"
)


libraryDependencies += "com.softwaremill.macwire" %% "macros" % "2.2.+" % "provided"


libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.0" % "test"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc-test" % scalikeVersion % "test"
libraryDependencies += "com.h2database" % "h2" % "1.4.+" % "test"
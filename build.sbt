name := "user_etl"

version := "1.0"

scalaVersion := "2.11.7"

mainClass in Compile := Some("by.arc.main.Main")

// assembly configuration
mainClass in assembly := Some("by.arc.main.Main")
test in assembly := {}
assemblyJarName in assembly := "user-etl.jar"
assemblyMergeStrategy in assembly := {
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case y =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(y)
}

val playVersion = "2.5.+"
val scalikeVersion = "2.4.+"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-ws" % playVersion,
  "com.typesafe.play" %% "play-json" % playVersion,
  "com.typesafe" % "config" % "1.3.+",
  "com.softwaremill.macwire" %% "macros" % "2.2.+" % "provided",
  "org.scalikejdbc" %% "scalikejdbc" % scalikeVersion,
  "org.scalikejdbc" % "scalikejdbc-config_2.11" % scalikeVersion,
  "org.postgresql" % "postgresql" % "9.4.1209.jre7",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.apache.commons" % "commons-dbcp2" % "2.1.+",

  // test dependencies
  "org.scalatest" %% "scalatest" % "3.0.+" % "test",
  "com.h2database" % "h2" % "1.4.+" % "test"
).map(_.exclude("commons-logging", "commons-logging").excludeAll(ExclusionRule(organization = "commons-logging")))

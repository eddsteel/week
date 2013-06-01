name := "week"

resourceDirectory in Compile := file("resources")

scalaSource in Test := file("test")

version := "0.1"

scalaVersion := "2.10.0"

scalaSource in Compile := file("src")

resourceDirectory in Compile := file("resources")


libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.2",
  "org.joda" % "joda-convert" % "1.2",
  "com.typesafe" % "config" % "1.0.1",
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"
)

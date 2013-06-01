scalaSource in Compile := file("src")

scalaSource in Test := file("test")


scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.2",
  "org.joda" % "joda-convert" % "1.2",
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"
)

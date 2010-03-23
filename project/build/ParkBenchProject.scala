import sbt._
class ParkBenchProject(info: ProjectInfo) extends DefaultProject(info) with posterous.Publish {
  val snapshots = "Scala Tools Snapshots" at "http://www.scala-tools.org/repo-snapshots/"
  val specs = "org.scala-tools.testing" % "specs" % "1.6.2.1-SNAPSHOT" % "test"
}
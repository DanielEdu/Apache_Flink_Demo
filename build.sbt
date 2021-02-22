name := "Flink-Demo"

version := "0.1"

scalaVersion := "2.12.11"
val flinkVersion = "1.9.1"

val flinkDependencies = Seq(
  "org.apache.flink" %% "flink-scala" % flinkVersion,
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion,
  "org.apache.flink" %% "flink-connector-kafka" % flinkVersion,
  "org.apache.flink" %% "flink-connector-kafka-base" % flinkVersion,
  "org.apache.flink" %% "flink-cep" % flinkVersion
)

lazy val root = (project in file(".")).
  settings(libraryDependencies ++= flinkDependencies)



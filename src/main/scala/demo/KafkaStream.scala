package demo

import java.util.Properties
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.common.serialization.SimpleStringSchema

object KafkaStream extends App {
  case class WordWithCount(word: String, count: Long)

  val properties = new Properties()
  properties.setProperty("bootstrap.servers", "localhost:9092")
  properties.setProperty("group.id", "0")

  val senv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
  val stream = senv
    .addSource(new FlinkKafkaConsumer[String]("infodemo", new SimpleStringSchema(), properties))

  private val windowCounts = stream
    .flatMap { w => w.split("\\s") }
    .map { w => WordWithCount(w, 1) }
    .keyBy("word")
    .timeWindow(Time.seconds(5), Time.seconds(5))
    .sum("count")

  windowCounts.print().setParallelism(1)

  senv.execute("kafka Window WordCount")
}

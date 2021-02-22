package demo

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object StreamSocketNetCat extends App {
  println("Socket NetCat")
  case class WordWithCount(word: String, count: Long)

  // get the execution environment
  val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

  val text = env.socketTextStream("localhost", 9000, '\n')

  val windowCounts = text
    .flatMap { w => w.split("\\s") }
    .map { w => WordWithCount(w, 1) }
    .keyBy("word")
    .timeWindow(Time.seconds(5), Time.seconds(5))
    .sum("count")

  windowCounts.print().setParallelism(1)

  env.execute("Socket_NetCat")
}

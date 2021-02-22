package demo

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.streaming.api.scala._

object Batch {
  case class WordWithCount(word: String, count: Long)

  def main(args: Array[String]): Unit = {
    println("Batch...")
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    // get input data
    val path: String = "/Users/user/data_source/textfile/gameofthrones.txt"
    val text: DataSet[String] = env.readTextFile(path,"UTF-8")

    val counts: DataSet[(String, Int)] = text
      .flatMap { _.toLowerCase.split("\\W+") }
      .map { (_, 1)}
      .groupBy(0)
      .sum(1)
      .filter( _._2 > 300)
      .sortPartition(1,Order.DESCENDING)

    counts.print()
    println("fin")

  }
}
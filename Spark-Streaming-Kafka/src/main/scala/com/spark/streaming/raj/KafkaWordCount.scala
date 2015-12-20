package com.spark.streaming.raj

/**
 * @author Kumar
 */
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf

object ScalaWordCount {

  def main(args: Array[String]) {
    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topic> <numThreads>")
      System.exit(1)
    }
    val Array(zkQuorum, group, topic, numThreads) = args
    val sparkConf = new SparkConf().setAppName("KafkaWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(15)) //s
    //ssc.checkpoint("checkpoint")
    //val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap //if there are multiple topics.
    val topicMap = topic.map((x => (x.toString, numThreads.toInt))).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
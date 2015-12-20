package com.spark.streaming.raj

/**
 * @author Kumar
 */
import scala.io.Source
import kafka.producer.ProducerConfig
import java.util.Properties
import kafka.producer.Producer
import kafka.producer.KeyedMessage
object KafkaProducer {
  def main(args: Array[String]): Unit = {

    if (args.length < 3) {
      System.err.println("Usage: KafkaProducer <brokers> <topic> <dataFile>")
      System.exit(1)
    }

    val brokers = args(0)
    val topic = args(1)
    val filename = args(2)
    var message = ""
    var data = new KeyedMessage[String, String](topic, message)
    val props = new Properties()
    props.put("metadata.broker.list", brokers)
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    props.put("request.required.acks", "1")
    
    val config = new ProducerConfig(props)
    val producer = new Producer[String, String](config)
    var counter=1
    while(true){
      try {
      for (line <- Source.fromFile(filename).getLines()) {
        message+=line
        if(counter%10==0){ //sending 10 lines from file at a time.
          data = new KeyedMessage[String, String](topic, message)
          producer.send(data)
          println(message)
          Thread.sleep(2000)
        }
        counter+=1
      }
    } catch {
      case ex: Exception => println("File IO exception happened.")
    }
    }
    

    producer.close()
  }
}
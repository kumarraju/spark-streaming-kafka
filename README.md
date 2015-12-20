# spark-streaming-kafka
Spark Streaming with Kafka

Step by Step Guide to install Kafka and integration with Spark Streaming.

1. Download the current release of Kafka

      http://kafka.apache.org/downloads.html

2. Extract the downloaded kafka*.tgz file

      tar xzf kafka*.tgz

3. Add the Kafka bin folder to PATH

Ex:- 
    export KAFKA_HOME=/opt/kafka_2.10-0.8.1.1

    export PATH=$PATH:$KAFKA_HOME/bin

4. Starting the ZooKeeper server

    zookeeper-server-start.sh /opt/kafka_2.10-0.8.1.1/config/zookeeper.properties

5. Starting the Kafka broker

    kafka-server-start.sh /opt/kafka_2.10-0.8.1.1/config/server.properties

6. Creating a Kafka topic

    kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kafkatopic

7. Check if kafka topic is created. 

    kafka-topics.sh --list --zookeeper localhost:2181

8. Starting a producer to send messages

      java -cp Streaming-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.spark.streaming.raj.KafkaProducer localhost:9092 kafkatopic   /home/cloudera/spark-training/appLoader-5837.log

    Note:- put any log file in current directory to read. (program will read the log file in indefinite loop and send 10 lines after reading from file.)

9. Start the Kafka word count (it's a kafka consumer which will read the data from kafka topic and create one RDD, apply neccesary transformation and produce word count.)

    spark-submit --class com.spark.streaming.raj.ScalaWordCount --master yarn-client     Streaming-0.0.1-SNAPSHOT-jar-with-dependencies.jar localhost:2181 test-consumer-group kafkatopic 1


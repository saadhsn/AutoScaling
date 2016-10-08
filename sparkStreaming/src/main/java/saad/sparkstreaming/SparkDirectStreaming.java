package saad.sparkstreaming;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.io.*;

import scala.Tuple2;

import kafka.serializer.StringDecoder;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.apache.spark.streaming.Durations;

/**
 * Consumes messages from one or more topics in Kafka.
 * Usage: SparkDirectStreaming.java <brokers> <topics>
 *   <brokers> is a list of one or more Kafka brokers
 *   <topics> is a list of one or more kafka topics to consume from
 *
 * Example:
 *    $ Have to add running example
 */
public final class SparkDirectStreaming {
  private static final Pattern SPACE = Pattern.compile(" ");
  
  public static void main (String[] args) throws Exception {
    if (args.length < 2) {
      System.err.println( "Usage: SparkDirectStreaming <brokers> <topics>/n" +
	     " <brokers> is a list of one or more Kafka brokers/n" +
	     " <topics> is a list of one or more Kafka topics to conusme from/n'n");
    }

    String brokers = args[0];
    String topics = args[1];

    // Creates context with 1 second batch interval 
    SparkConf sparkConf = new SparkConf().setAppName("SparkDirectStreaming");
    JavaStreamingContext jssc = new JavaStreamingContext (sparkConf , Durations.seconds(1));

    Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
    Map<String, String> kafkaParams = new HashMap<>();
    kafkaParams.put("metadata.broker.list", brokers);

    JavaPairInputDStream<String, String> messages = KafkaUtils.createDirectStream(
        jssc,
        String.class,
        String.class,
        StringDecoder.class,
        StringDecoder.class,
        kafkaParams,
        topicsSet
    );


    // Get the lines, split them into words, count the words and print
    JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
      @Override
      public String call(Tuple2<String, String> tuple2) {
        return tuple2._2();
      }
    });

    // Convert lines into words 
    JavaDStream <String> words=lines.flatMap(word -> Arrays.asList(word.split(" ")));
    // Map words with key and count the words
    JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
      new PairFunction<String, String, Integer>() {
        @Override
        public Tuple2<String, Integer> call(String s) {
          return new Tuple2<>(s, 1);
        }
      }).reduceByKey(
        new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer call(Integer i1, Integer i2) {
          return i1 + i2;
        }
      });

     wordCounts.print();
     jssc.start();
     jssc.awaitTermination();
  }

}

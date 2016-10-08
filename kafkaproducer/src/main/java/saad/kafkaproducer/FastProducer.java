package saad.kafkaproducer;
/*Libraries for Apache Kafak*/
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*Java util libraries*/
import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class FastProducer {
  private static final Logger logger = LoggerFactory.getLogger(FastProducer.class);
  private final String csvSplitBy = ",";
  private String CSVPath = null;
  private String topicName = null;
  private BufferedReader bufferedReader = null;
  private KafkaProducer<String, String> kafkaProducer = null;

  private void setCSVPath(String CSVPath) {
    /*Sets path to a CSV file containing multiple rows*/
    this.CSVPath = CSVPath;
    logger.info("[JobEventsProducer] - Path for a CSV file - " + this.CSVPath);
  }

  private void setTopicName(String topicName) {
    this.topicName = topicName;
    logger.info("[JobEventsProducer] - Topic name - " + this.topicName);
  }

  private void setBufferedReader() {
    /*Initializes a BufferedReader to read the CSV file
    * Class scope variable used to keep track of the last read line pointer location*/
    try {
      this.bufferedReader = new BufferedReader(new FileReader(this.CSVPath));
    } catch (IOException e) {e.printStackTrace (); }
  }

  private void setKafkaProducer() {
    /*Initialized a KafkaProducer that accepts ProducerRecords of type String*/
    Properties props = new Properties();
    props.put("zk.connect", "127.0.0.1:2181");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("bootstrap.servers", "localhost:9092");
    this.kafkaProducer = new KafkaProducer<String, String>(props);
  }
    

  private void closeKafkaProducer() {
    this.kafkaProducer.close();
    logger.info("[JobEventsProducer] - Kafka producer closed");
  }

  void readRecordsFromCSV() {
    String line = null;
    int records= 0;
    for(int counter=0;counter<100;counter++) {
      try { 
        line=this.bufferedReader.readLine();
	counter++;
      } catch (IOException e) {e.printStackTrace(); }
      this.kafkaProducer.send(new ProducerRecord<String, String>(this.topicName, line));
      records++;
    }
    System.out.println("Number of records sent : " + records);
    }
    
  
   
  public static void main(String[] args) {

    FastProducer jobEventsProducer = new FastProducer();
    jobEventsProducer.setCSVPath("/home/saad_hussain/sampleData/trigramSeq_10M");
    jobEventsProducer.setTopicName("fast");
    jobEventsProducer.setBufferedReader();
    jobEventsProducer.setKafkaProducer();
    jobEventsProducer.readRecordsFromCSV();
    jobEventsProducer.closeKafkaProducer();

  }

}


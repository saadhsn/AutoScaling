# auto-scaling

### running kafkaproducer  
- `cd kafkaproducer`  
- compiling code `mvn clean install`  
- `cd target` this directory contains two jar files  
- only run jar file with dependencies in this case *kafka-1.0-SNAPSHOT-jar-with-dependencies.jar* 
- **Important** you need to add your own data path in Kafka producer code, class name is *SimpleProducer.java* and data can be downloaded from here [jobEvents_data(CSV Fromat)](https://commondatastorage.googleapis.com/clusterdata-2011-2/job_events/part-00001-of-00500.csv.gz)   
- running jar file `java -cp pathToJar/kafka-1.0-SNAPSHOT-jar-with-dependencies.jar: saad.kafkaproducer.SimpleProducer`  
- running jar file `java -cp pathToJar/kafka-1.0-SNAPSHOT-jar-with-dependencies.jar: saad.kafkaproducer.FastProducer`  
- *SimpleProducer* class reads whole data from csv file locally stored  
- In *FastProducer* class you can change the size of data in while loop for debugging purpose  


### running sparkStreamings
- `cd sparkStreaming`  
- compiling code `mvn clean install`  
- `cd target` this directory contains two jar files  
- only run jar file with dependencies in this case *sparkStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar*  
- for submitting spark application:  
- `bin/spark-submit --class saad.sparkstreaming.SparkDirectStreaming --master local[n] /pathToJar/sparkStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar localhost:9092 jobEvents`  
- where **localhost:9092** and **jobEvents** are kafka broker list and kafka topic name respectively  



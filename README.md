# auto-scaling

### running kafkaproducer  
- `cd kafkaproducer`  
- compiling code `mvn clean install`  
- `cd target` this directory contains two jar files  
- only run jar file with dependencies in this case *kafka-1.0-SNAPSHOT-jar-with-dependencies.jar*  
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



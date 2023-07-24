package com.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;


public class OrderProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers","localhost:9092");
       // props.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
      //  props.setProperty("value.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        props.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

     // KafkaProducer<String, Integer> producer = new KafkaProducer<String,Integer>(props);
        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(props);
        ProducerRecord<Integer,String> record = new ProducerRecord<>("OrderTopic",10,"22.5726N,88.3639E");
        try{
           // Future<RecordMetadata> future = producer.send(record);
       //     RecordMetadata recordMetadata = producer.send(record).get();
            producer.send(record, new OrderCallback()).get();

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            producer.close();
        }

    }

}

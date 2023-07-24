package com.kafka;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class orderConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      //  props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
      //  props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "OrderGroup");
        props.setProperty(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "10241024");
        props.setProperty(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG,"200");
        props.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "3000");
        //30 partitions, 5 consumers, 6MB - 12MB
        props.setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "1MB");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); //or could be earliest
        props.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "OrderConsumer");
        props.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
        //Partition Assignoer Strategy
       // props.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RangeAssignor.class.getName()); default
        props.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());


        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);
     //   consumer.subscribe(Collections.singletonList("OrderTopic"));
        consumer.subscribe(Collections.singletonList("OrderTopic"));

        ConsumerRecords<Integer, String> orders = consumer.poll(Duration.ofSeconds(20));

        for(ConsumerRecord<Integer, String> order : orders){
            System.out.println("Id : " + order.key());
            String  value = order.value();
            String values[] =value.split(",");
            System.out.println("Latitude     : " + values[0]);
            System.out.println("Longitude     : " + values[1]);
        }
        consumer.close();
    }
}
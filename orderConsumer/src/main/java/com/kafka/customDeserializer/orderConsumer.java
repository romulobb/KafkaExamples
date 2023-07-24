package com.kafka.customDeserializer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class orderConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.deserializer", StringDeserializer.class.getName());
        props.setProperty("value.deserializer", orderDeserializer.class.getName());
        props.setProperty("group.id", "OrderGroup");
        props.setProperty("auto.commit.intervals.ms","2000");
        props.setProperty("auto.commit.offset","false");



        KafkaConsumer<String, Order> consumer = new KafkaConsumer<>(props);

        class RebalanceHandler implements ConsumerRebalanceListener{

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {

            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {

            }
        }

        consumer.subscribe(Collections.singletonList("OrderPartitionedTopic"),new RebalanceHandler());

        try{
            while(true){
                ConsumerRecords<String,Order> records = consumer.poll(Duration.ofSeconds(20));
                int count = 0;
                for(ConsumerRecord<String,Order> record : records){
                    String customerName = record.key();
                    Order order = record.value();
                    System.out.println("Customer Name : " + customerName);
                    System.out.println("Product     : " + order.getProduct());
                    System.out.println("Quantity    : " + order.getQuantity());
                    System.out.println("Partition    : " + record.partition());
                    if (count%10==0){
                        consumer.commitAsync(Collections.singletonMap(new TopicPartition(record.topic(), record.partition()),
                                new OffsetAndMetadata(record.offset()+1)),
                                new OffsetCommitCallback() {
                            @Override
                            public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                                if (e != null){
                                    System.out.println("Commit failed for offset"+map);
                                }
                            }
                        }); //do not retry, for not giving possibility to duplicates, or lost index

                    }
                    count++;
                }
               // consumer.commitSync(); block the app until the broker response back to the request, do retries



            }
        } finally {
        consumer.close();}
    }
}
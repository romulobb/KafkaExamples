package com.kafka.customSerializers;

import com.kafka.OrderCallback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


public class OrderProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers","localhost:9092");
        props.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer","com.kafka.customSerializers.orderSerializer");

        KafkaProducer<String, Order> producer = new KafkaProducer<String,Order>(props);
        Order order = new Order();
        order.setCustomerName("PepeLui");
        order.setProduct("Producto");
        order.setQuantity(1);
        ProducerRecord<String, Order> record = new ProducerRecord<>("OrderCSTopic",order.getCustomerName() , order);
        try{
           // Future<RecordMetadata> future = producer.send(record);
       //     RecordMetadata recordMetadata = producer.send(record).get();
            producer.send(record);

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            producer.close();
        }

    }

}

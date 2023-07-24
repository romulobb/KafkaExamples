package com.kafka.avro.serializer;

import com.kafka.avro.Order;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


public class OrderProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers","localhost:9092");
        props.setProperty("key.serializer", KafkaAvroSerializer.class.getName() );
        props.setProperty("value.serializer",KafkaAvroSerializer.class.getName() );
        props.setProperty("schema.registry.url","http://localhost:8081");

        KafkaProducer<String, Order> producer = new KafkaProducer<String,Order>(props);
        Order order = new Order("pepeLui","Producto",3);

        ProducerRecord<String, Order> record = new ProducerRecord<>("OrderAvroTopic",order.getCustomerName().toString() , order);
        try{
            producer.send(record);

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            producer.close();
        }

    }

}

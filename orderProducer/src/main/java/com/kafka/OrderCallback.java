package com.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class OrderCallback implements Callback {


    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        System.out.println(" Partition :"+recordMetadata.partition());
        System.out.println(" Offset    :"+recordMetadata.offset());
        System.out.println(" Message Sent Successfuly");
        if (e!=null){
            e.printStackTrace();
        }
    }
}

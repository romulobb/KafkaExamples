package com.kafka.Service;

import com.kafka.dto.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class userConsumerService {

    @KafkaListener(topics = {"user-topic"})
    public void consumerUserData(User user){
        System.out.println("Users Age is "+ user.getAge()+" Fav Genere "+user.getFavGenere());

    }
}

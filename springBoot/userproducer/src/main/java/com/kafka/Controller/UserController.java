package com.kafka.Controller;

import com.kafka.Service.UserProducerService;
import com.kafka.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userapi")
public class UserController {

    @Autowired
    private UserProducerService service;

    @PostMapping("/publishUserData")
    public void sendUserData(@RequestBody User user){
        service.sendUserData(user);
    }
}

package com.user_service.User.service;



import com.user_service.User.Models.User;
import com.user_service.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Object updateUser(Map<String, String> value) {
        System.out.println(value.containsKey("id")+"--"+value.keySet());
        if(value.containsKey("id")){
            User user=userRepository.findById(Integer.parseInt(value.get("id")))
                    .orElse(new User());
            Set<String> keys=value.keySet();
            Iterator<String> iterator=keys.iterator();

            while (iterator.hasNext()){
                String key=iterator.next();

                if(key.equals("id"))continue;

                switch (key){
                    case "username":
                        user.setUserName(value.get("username"));
                        break;
                }
            }


            return userRepository.save(user);
        }

        return new User();
    }
}

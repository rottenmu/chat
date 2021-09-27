package com.open.im.application.api;

import com.open.im.application.service.UserService;
import com.open.im.commons.entity.BrokerInfo;
import com.open.im.commons.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public BrokerInfo login(@RequestBody User user){
        return userService.login(user);
    }

    @GetMapping(value = "/{phone}")
    public User getUser(@PathVariable String phone){
        return userService.getUser(phone);
    }
}

package com.open.im.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.open.im.application.service.RedisService;
import com.open.im.application.service.UserService;
import com.open.im.commons.entity.BrokerInfo;
import com.open.im.commons.entity.User;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService<User> redisService;

    @Override
    public BrokerInfo login(User user) {
        redisService.put(user.getPhone(), user);
        try {
            String res = EntityUtils.toString(Request.Get("http://127.0.0.1:27002/broker").execute().returnResponse().getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(String phone) {
        return redisService.get(phone);
    }
}

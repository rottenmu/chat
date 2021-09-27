package com.open.im.application.service;

import com.open.im.commons.entity.BrokerInfo;
import com.open.im.commons.entity.User;
import reactor.core.publisher.Mono;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public interface UserService {
    BrokerInfo login(User user);
    User getUser(String phone);
}

package com.open.im.broker.endpoint;

import com.open.im.broker.biz.BrokerInfoService;
import com.open.im.broker.cluster.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@RestController
public class BrokerEndpoint {
    @Autowired
    private BrokerInfoService brokerInfoService;

    @GetMapping(value = "/broker")
    public Instance getBroker(){
        return brokerInfoService.getInstance();
    }

}

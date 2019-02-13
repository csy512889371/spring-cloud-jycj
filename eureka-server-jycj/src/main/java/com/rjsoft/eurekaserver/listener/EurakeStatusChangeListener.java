package com.rjsoft.eurekaserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class EurakeStatusChangeListener {
    @EventListener
    public void onApplicationEvent(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        log.info("服务：{} 挂掉了", eurekaInstanceCanceledEvent.getAppName());
    }

    @EventListener
    public void onApplicationEvent(EurekaInstanceRegisteredEvent eurekaInstanceRegisteredEvent) {
        log.info("服务：{}， 注册成功了", eurekaInstanceRegisteredEvent.getInstanceInfo().getAppName() + "->节点:" + eurekaInstanceRegisteredEvent.getInstanceInfo().getIPAddr());
    }

    @EventListener
    public void onApplicationEvent(EurekaInstanceRenewedEvent eurekaInstanceRenewedEvent) {
        log.info("心跳检测服务：{}", eurekaInstanceRenewedEvent.getAppName());
    }
}

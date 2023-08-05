package com.bx.imserver.netty;

public interface IMServer {

    /**
     * 服务是否准备就绪
     * @return
     */
    boolean isReady();

    /**
     * 服务开启
     */
    void start();

    /**
     * 服务停止
     */
    void stop();
}

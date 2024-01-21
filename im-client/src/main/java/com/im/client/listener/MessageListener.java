package com.im.client.listener;


import com.im.common.model.SendResult;

public interface MessageListener {

     void process(SendResult result);

}

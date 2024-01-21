package com.im.client.listener;


import com.im.client.annotation.IMListener;
import com.im.common.enums.IMListenerType;
import com.im.common.model.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MessageListenerMulticaster {

    @Autowired(required = false)
    private List<MessageListener>  messageListeners  = Collections.emptyList();

    public  void multicast(IMListenerType type, SendResult result){
        for(MessageListener listener:messageListeners){
            IMListener annotation = listener.getClass().getAnnotation(IMListener.class);
            if(annotation!=null && (annotation.type().equals(IMListenerType.ALL) || annotation.type().equals(type))){
                listener.process(result);
            }
        }
    }
}

package com.mmq.rabbitTest.consumer;

import com.mmq.rabbitTest.producer.MessageProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by Derry on 2017/2/22.
 */
public class MessageConsumer implements MessageListener {

    private static final Logger logger = LogManager.getLogger(MessageConsumer.class);
    public void onMessage(Message message) {

        String messageString;
        try {
            messageString =  new String(message.getBody(),"utf-8");
            String messageid = message.getMessageProperties().getMessageId();
            logger.info("recieve message:{},messageid:{}",messageString, messageid);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            messageString = null;
        }

    }
}

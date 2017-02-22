package com.mmq.rabbitTest.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * Created by Derry on 2017/2/22.
 */
@Component
public class MessageProducer {

    private static final Logger logger = LogManager.getLogger(MessageProducer.class);

    @Resource
    private AmqpTemplate amqpTemplate;


    public void sendMessage(String messageString, String key){

        logger.info("send message:{}, key:{}", messageString, key);

        try {
            Message message = MessageBuilder.withBody(messageString.getBytes("utf-8"))
                    .setMessageId(System.currentTimeMillis()+"")
                    .build();

            this.amqpTemplate.convertAndSend(key, message);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
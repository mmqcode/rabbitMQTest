package com.mmq.rabbitTest.producer;

import com.mmq.rabbitTest.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


    /**发送一条文本消息
     * @param messageString
     * @param key
     */
    public void sendMessage(String messageString, String key){

        logger.info("send message:{}, key:{}", messageString, key);

        try {
            Message message = MessageBuilder.withBody(messageString.getBytes("utf-8"))
                    .setMessageId(System.currentTimeMillis()+"")
                    .build();
            this.amqpTemplate.send(key, message);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void sendPojoMessage(String key){
        User user = new User();
        user.setAccount("mmq");
        user.setName("莫敏强");
        logger.info("send message:{}, key:{}", user, key);
        try {
            //发送pojo消息，需要pojo实现serializable接口
            this.amqpTemplate.convertAndSend(key, user);
        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
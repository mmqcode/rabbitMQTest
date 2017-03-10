package com.mmq.rabbitTest.consumer;

import com.google.gson.Gson;
import com.mmq.rabbitTest.producer.MessageProducer;
import com.mmq.rabbitTest.tool.IoTool;
import com.mmq.rabbitTest.vo.TextMessage;
import com.mmq.rabbitTest.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * Created by Derry on 2017/2/22.
 */
@Component("messageConsumer")
public class MessageConsumer implements MessageListener {

    private static final Logger logger = LogManager.getLogger(MessageConsumer.class);
    @Autowired
    private IoTool ioTool;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Gson gson = new Gson();

    public void onMessage(Message message) {

        String messageString;
        User user;
        TextMessage textMessage;
        try {
//            String messageid = message.getMessageProperties().getMessageId();
//
//            messageString =  new String(message.getBody(),"utf-8");
//            //将消息放入缓存中。
//            stringRedisTemplate.opsForList().rightPush("message",  messageString);
//            logger.info("receive message:{},messageid:{}",messageString, messageid);
////            user = (User)ioTool.toObject(message.getBody());
////            logger.info("receive message:{},messageid:{}",user, messageid);
            textMessage = (TextMessage)ioTool.toObject(message.getBody());

            //将消息按目标对象不同存放到缓存中。
            String jsonMessage = gson.toJson(textMessage);
            stringRedisTemplate.opsForList()
                    .rightPush("textMessage:"+textMessage.getMessageDestination(), jsonMessage);
        }catch (Exception e){
            e.printStackTrace();
            messageString = null;
        }

    }

    public IoTool getIoTool() {
        return ioTool;
    }

    public void setIoTool(IoTool ioTool) {
        this.ioTool = ioTool;
    }
}

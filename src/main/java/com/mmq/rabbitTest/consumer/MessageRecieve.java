package com.mmq.rabbitTest.consumer;
import com.mmq.rabbitTest.vo.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Derry on 2017/2/22.
 */
@Service
public class MessageRecieve {

    @Resource
    private AmqpTemplate amqpTemplate;


    public Message recieveMessage(String queueName){

        Message message = this.amqpTemplate.receive(queueName, 500l);
        return message;
    }

    public User recievePojoMessage(String queueName){
        User user;
        try{
            user = (User)this.amqpTemplate.receiveAndConvert(queueName, 500l);
            System.out.println(user);
        }catch (Exception e){
            e.printStackTrace();
            user = null;
        }
        return user;
    }

}

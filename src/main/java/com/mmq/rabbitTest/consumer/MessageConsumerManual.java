package com.mmq.rabbitTest.consumer;

import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/2/23.
 */
@Component("manualReceiver")
public class MessageConsumerManual implements ChannelAwareMessageListener {

    private static final Logger logger = LogManager.getLogger(MessageConsumerManual.class);
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        String messageString;
        try {
            messageString =  new String(message.getBody(),"utf-8");
            String messageid = message.getMessageProperties().getMessageId();
            logger.info("recieve message:{},messageid:{}",messageString, messageid);
            //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            messageString = null;
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}

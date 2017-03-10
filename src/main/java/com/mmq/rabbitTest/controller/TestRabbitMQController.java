package com.mmq.rabbitTest.controller;

import com.google.gson.Gson;
import com.mmq.rabbitTest.consumer.MessageRecieve;
import com.mmq.rabbitTest.producer.MessageProducer;
import com.mmq.rabbitTest.tool.IoTool;
import com.mmq.rabbitTest.tool.JsonTool;
import com.mmq.rabbitTest.vo.TextMessage;
import com.mmq.rabbitTest.vo.User;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Derry on 2017/2/22.
 */
@Controller
@RequestMapping("/testRabbitMQController")
public class TestRabbitMQController {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MessageRecieve messageRecieve;

    @Autowired
    private JsonTool jsonTool;

    @Autowired
    private IoTool ioTool;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Gson gson = new Gson();

    @RequestMapping(value = "/showProducer")
    public ModelAndView showMessageProducerPage(){
        String targetPage = "messageProduce";
        Map<String, String> map = null;

        return new ModelAndView(targetPage, "model", map);
    }

    @RequestMapping(value = "/doSendMessage", method = RequestMethod.POST)
    public void doSendMessage(String message, String key, HttpServletResponse response){


        this.messageProducer.sendMessage(message, key);

        String result = this.jsonTool.getSimpleMsgJson("操作成功!","0");
        this.ioTool.writeMessageResponse(result, response);
    }

    @RequestMapping(value = "/doSendTextMessage", method = RequestMethod.POST)
    public void doSendTextMessage(String content, String key, HttpServletResponse response){

        TextMessage textMessage = new TextMessage();
        textMessage.setContent(content);
        textMessage.setMessageDestination("mmq");
        textMessage.setMessageSource("system");
        textMessage.setSendTime(new Date());
        this.messageProducer.sendTextMessage(textMessage, key);

        String jsonResult = this.jsonTool.getSimpleMsgJson("操作成功!", "0");
        this.ioTool.writeMessageResponse(jsonResult, response);
    }

    @RequestMapping(value = "/sendPojoMessage", method = RequestMethod.POST)
    public void sendPojoMessage(String key, HttpServletResponse response){
        this.messageProducer.sendPojoMessage(key);
        String result = this.jsonTool.getSimpleMsgJson("操作成功!","0");
        this.ioTool.writeMessageResponse(result, response);
    }

    @RequestMapping(value = "/sendMessageTofanout", method = RequestMethod.POST)
    public void sendMessageToFanout(String message, HttpServletResponse response){
        this.messageProducer.sendMessageToFanout(message);
        String result = this.jsonTool.getSimpleMsgJson("操作成功!","0");
        this.ioTool.writeMessageResponse(result, response);
    }

    @RequestMapping(value = "/sendMessageToTopic", method = RequestMethod.POST)
    public void sendMessageToTopic(String message, String pattern, HttpServletResponse response){

        this.messageProducer.sendMessageToTopic(message, pattern);
        String result = this.jsonTool.getSimpleMsgJson("操作成功!","0");
        this.ioTool.writeMessageResponse(result, response);
    }

    @RequestMapping(value = "/showRecieveMessagePage")
    public ModelAndView showRecieveMessagePage(){
        String targetPage = "messageReceive";

        return new ModelAndView(targetPage, "model", new Object());
    }


    @RequestMapping(value = "/recieveMessage", method = RequestMethod.POST)
    public void recieveMessage(String queueName,HttpServletResponse response){

        Message message = messageRecieve.recieveMessage(queueName);
        String resultJson;
        try{
            if(null != message){
                String bodyMessage =  new String(message.getBody(), "utf-8");
                resultJson = this.jsonTool.getSimpleMsgJson(bodyMessage, "0");
            }else{
                throw new Exception("接收不到消息了!");
            }
        }catch (Exception e){
            resultJson = this.jsonTool.getSimpleMsgJson(e.getMessage(), "1");
        }
        this.ioTool.writeMessageResponse(resultJson, response);
    }

    @RequestMapping(value = "/recievePojoMessage", method = RequestMethod.POST)
    public void recievePojoMessage(String queueName, HttpServletResponse response){

        User user = messageRecieve.recievePojoMessage(queueName);
        String resultJson;
        try{

            if(null != user){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code","0");
                map.put("user", user);
                resultJson = new Gson().toJson(map);
            }else{
                throw new Exception("接收不到消息了!");
            }


        }catch (Exception e){
            resultJson = this.jsonTool.getSimpleMsgJson(e.getMessage(), "1");
        }
        this.ioTool.writeMessageResponse(resultJson, response);
    }

    @RequestMapping(value="/showStoreMessage", method = RequestMethod.GET)
    public ModelAndView showStoreMessage(){
        String tagetPage = "showStoreMessage";
        Map<String, Object> resultMap = new HashMap<>();
        List<String> messageList = this.stringRedisTemplate.opsForList().range("message",0, -1);

        resultMap.put("messages", messageList);
        return new ModelAndView(tagetPage, "model", resultMap);
    }

    @RequestMapping(value = "/longPollingGetMessage", method = RequestMethod.POST)
    public void longPollingGetMessage(HttpServletResponse response){

        long startTime = System.currentTimeMillis();
        long currentTime;
        long waitTimeout = 9000l;
        //当前用户
        String currentUser = "mmq";
        Map<String, Object> resultMap = new HashMap<>();
        while(true){

            String jsonTextMessage = this.stringRedisTemplate.opsForList().leftPop("textMessage:"+currentUser);
            if(null != jsonTextMessage){

                TextMessage textMessage = this.gson.fromJson(jsonTextMessage, TextMessage.class);

                resultMap.put("code","0");
                resultMap.put("msg", "获取到消息!");
                resultMap.put("textMessage", textMessage);
                break;
            }
            currentTime = System.currentTimeMillis();
            if(currentTime - startTime > waitTimeout){
                resultMap.put("code","1");
                resultMap.put("msg", "超时!");
                break;
            }
        }

        String resultJson = this.gson.toJson(resultMap);
        this.ioTool.writeMessageResponse(resultJson, response);

    }



}

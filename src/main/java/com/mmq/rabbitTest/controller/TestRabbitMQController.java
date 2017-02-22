package com.mmq.rabbitTest.controller;

import com.mmq.rabbitTest.producer.MessageProducer;
import com.mmq.rabbitTest.tool.IoTool;
import com.mmq.rabbitTest.tool.JsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    private JsonTool jsonTool;

    @Autowired
    private IoTool ioTool;

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
}

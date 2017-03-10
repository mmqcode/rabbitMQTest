package com.mmq.rabbitTest.controller;

import com.mmq.rabbitTest.cache.RedisMethodTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/3/7.
 */
@Controller
@RequestMapping("/testRedisController")
public class TestRedisContronller {

    @Autowired
    private RedisMethodTest redisMethodTest;

    @RequestMapping(value="/showTestRedisPage")
    public ModelAndView showTestRedisPage(){
        String targetPage = "showTestRedisPage";
        return new ModelAndView(targetPage, "model", new String(""));
    }

    @RequestMapping(value = "/testConnection", method = RequestMethod.POST)
    public void testConnection(){
        this.redisMethodTest.userCallBack();
    }

    @RequestMapping(value="/testHashMapper", method = RequestMethod.POST)
    public void testHashMapper(){

        //this.redisMethodTest.useHashMapperToStoreMapObject();
        this.redisMethodTest.userJackson2HashMapper();
    }

    @RequestMapping(value = "/testTransaction", method = RequestMethod.POST)
    public void testTransaction(){
        this.redisMethodTest.testTransaction();

    }

    @RequestMapping(value = "/testSet", method = RequestMethod.POST)
    public void testSet(){

        this.redisMethodTest.testSet();

    }



}

package com.mmq.rabbitTest.tool;

import org.junit.Test;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/3.
 */
public class MethodTest {



    @Test
    public void testQueue(){
        QueueArray queueArray = new QueueArray();
        QueueLinkList queueLinkList = new QueueLinkList();
        System.out.println("start adding element to queueArray:"+ new Date());

        for(int i = 0; i < 10000; i++){
            queueArray.enqueue(i+"");
        }
        System.out.println("done:"+ new Date());

        System.out.println("start adding element to queueLinkList:"+ new Date());
        for(int i = 0; i < 10000; i++){
            queueLinkList.enqueue(i+"");
        }
        System.out.println("done:"+ new Date());

        System.out.println("start getting element from queueArray:"+ new Date());
        while(!queueArray.isEmpty()){
            queueArray.dequeue();
        }
        System.out.println("done:"+ new Date());


        System.out.println("start getting element from queueLinkList:"+ new Date());
        while(!queueLinkList.isEmpty()){
            queueLinkList.dequeue();
        }
        System.out.println("done:"+ new Date());
    }


}

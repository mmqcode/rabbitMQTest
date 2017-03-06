package com.mmq.rabbitTest.tool;

/**
 * Created by Administrator on 2017/3/2.
 */
public interface Queue<T> {

    Queue<T> enqueue(T element);
    T dequeue();
    boolean isEmpty();
}

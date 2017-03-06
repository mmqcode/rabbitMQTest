package com.mmq.rabbitTest.tool;

/**
 * Created by Administrator on 2017/3/3.
 */
public class QueueLinkList<T> implements Queue<T> {

    private int total;
    private Node first;
    private Node last;

    private class Node{
        private T element;
        private Node next;
    }
    @Override
    public QueueLinkList<T> enqueue(T element) {
        Node current = last;
        last = new Node();
        last.element = element;
        if(total == 0){
            first = last;
        }else{
            current.next = last;
        }
        total += 1;
        return this;
    }

    @Override
    public T dequeue() {

        if(total == 0){
            throw new java.util.NoSuchElementException();
        }
        T element = first.element;
        first = first.next;
        total -= 1;
        if(total == 0){
            last = null;
        }
        return element;
    }

    @Override
    public boolean isEmpty() {
        if(total == 0){
            return true;
        }
        return false;
    }
}

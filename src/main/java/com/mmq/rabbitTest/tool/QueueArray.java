package com.mmq.rabbitTest.tool;

/**array base queue  implements
 * Created by Administrator on 2017/3/2.
 */
public class QueueArray<T> implements Queue<T> {

    private T[] array;

    private int total;
    private int first;
    private int next;

    public QueueArray() {
        array = (T[]) new Object[2];
    }

    private void resize(int capacity){
        T[] temp = (T[]) new Object[capacity];
        for(int i = 0; i < total; i++){
            temp[i] = array[(first+i) % array.length];
        }
        array = temp;
        first = 0;
        next = total;
    }

    @Override
    public QueueArray<T> enqueue(T element) {

        if(array.length == total){
            resize(array.length * 2);
        }
        array[next] = element;
        next += 1;
        if(next == array.length){
            next = 0;
        }
        total += 1;
        return this;

    }
    @Override
    public T dequeue() {
        if(total == 0){
            throw new java.util.NoSuchElementException();
        }

        T element = array[first];
        array[first] = null;
        first += 1;
        if(first == array.length){
            first = 0;
        }
        total -= 1;
        if(total > 0 && total == array.length/4){
            resize(array.length/2);
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

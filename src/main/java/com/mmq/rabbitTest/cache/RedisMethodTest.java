package com.mmq.rabbitTest.cache;

import com.mmq.rabbitTest.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/7.
 */
@Service
public class RedisMethodTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public void userCallBack(){

        stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long size = connection.dbSize();
                System.out.println(size);
                ((StringRedisConnection)connection).set("connection", "http://www.baidu.com");
                return ((StringRedisConnection)connection);
            }
        });

    }

    public void useHashMapperToStoreMapObject(){
        HashOperations<String, byte[], byte[]> hasOperation = this.redisTemplate.opsForHash();
        HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
        User user = new User();
        user.setAccount("momq");
        user.setName("mominqiang");
        Map<byte[], byte[]> mappHash = mapper.toHash(user);
        String key = "user";
        hasOperation.putAll(key, mappHash);

        Map<byte[], byte[]> loadHash = hasOperation.entries(key);
        user = null;
        user = (User)mapper.fromHash(loadHash);
        System.out.println(user);
    }

    public void userJackson2HashMapper(){
        HashOperations<String, String, Object> hasOperation = this.redisTemplate.opsForHash();
        Jackson2HashMapper mapper = new Jackson2HashMapper(false);
        User user = new User();
        user.setAccount("momq");
        user.setName("mominqiang");
        Map<String, Object> mappHash = mapper.toHash(user);
        String key = "user2";
        hasOperation.putAll(key,mappHash);
        Map<String, Object> loadHash = hasOperation.entries(key);
        user = null;
        user = (User)mapper.fromHash(loadHash);
        System.out.println(user);
    }


    public void testTransaction(){
        List<Object> txResult = (List<Object>)redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForList().rightPush("animal", "pig");
                operations.opsForList().rightPush("country", "china");

                return operations.exec();
            }
        });
        System.out.println(txResult.get(0));
    }


    //集群cluster
    public void testCluster(){


    }

    public void testSet(){

        SetOperations<String, String> setOperations = this.redisTemplate.opsForSet();
        setOperations.add("fly", "birds", "bianfu", "qingting");
        setOperations.add("kunchong", "qingting", "hudie");
        Set<String> differenceSet = setOperations.difference("fly", "kunchong");
        System.out.println(differenceSet);
        setOperations.isMember("fly", "airplane");

        Set<String> interSet=setOperations.intersect("fly", "kunchong");

        System.out.println(interSet);

        setOperations.size("fly");

        setOperations.randomMember("fly");

        setOperations.pop("fly");


        ZSetOperations<String, String> zSetOperations = this.redisTemplate.opsForZSet();

        zSetOperations.add("animal","tiger", 100);
        zSetOperations.score("animal", "tiger");

        zSetOperations.incrementScore("animal","tiger", 10);
        zSetOperations.size("animal");


    }



}

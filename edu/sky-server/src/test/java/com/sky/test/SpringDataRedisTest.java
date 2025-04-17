package com.sky.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

@SpringBootTest
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate()
    {
System.out.println(redisTemplate);
//对5种主要的redis数据类型进行操作
        ValueOperations valueOperations=redisTemplate.opsForValue();
        HashOperations hashOperations=redisTemplate.opsForHash();
        ListOperations listOperations=redisTemplate.opsForList();
        SetOperations setOperations=redisTemplate.opsForSet();
        ZSetOperations zSetOperations=redisTemplate.opsForZSet();
        testString();
    }

    //操作String类型数据

    public void testString(){
        //set
redisTemplate.opsForValue().set("city","北京");
       String city= (String)redisTemplate.opsForValue().get("city");
        System.out.println("ok"+city);
    }
}

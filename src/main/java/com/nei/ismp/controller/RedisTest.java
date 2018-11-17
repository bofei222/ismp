package com.nei.ismp.controller;

import com.alibaba.fastjson.JSONObject;
import com.nei.ismp.pojo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author bofei
 * @Date 2018/11/17 14:48
 * @Description
 */
@RestController
public class RedisTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTest.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/aaa")
    public void setaaa() {
        stringRedisTemplate.opsForValue().set("aaa", "111");
    }

    @GetMapping("/aaa")
    public String getaaa() {
        return stringRedisTemplate.opsForValue().get("aaa");
    }

    @PostMapping("/mylist")
    public void lpushmylist(String s) {
        stringRedisTemplate.opsForList().leftPush("mylist", s);
    }

    @GetMapping("/mylist")
    public String rpopmylist() {
        return stringRedisTemplate.opsForList().rightPop("mylist");
    }

    @GetMapping("/mylist2")
    public void lpushlist2() {
//        stringRedisTemplate.opsForList().rightPop("mylist", 1, TimeUnit.SECONDS);
        List<String> mylist = stringRedisTemplate.opsForList().range("mylist", 0, -1);
        LOGGER.info("{}", mylist);
    }

    @PostMapping("/test10")
    public void test10() {
        User user = new User();
        user.setId(1);
        user.setName("bofei");
//        redisTemplate.opsForValue().set(user.getId(), user);
        redisTemplate.opsForValue().set(2, new JSONObject().put("AAA", "ZZZ"));
    }

    @GetMapping("/test11")
    public void test11() {
//        User o = (User) redisTemplate.opsForValue().get(1);
//        LOGGER.info("名称:{};id:{}", o.getName(), o.getId());
        JSONObject jo = (JSONObject) redisTemplate.opsForValue().get(2);
        LOGGER.info("jo:{}", jo);
    }
}

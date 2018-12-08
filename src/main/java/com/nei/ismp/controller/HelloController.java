package com.nei.ismp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nei.ismp.pojo.entity.User;
import com.nei.ismp.properties.IampProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RefreshScope
@CrossOrigin
public class HelloController {

    @Value("${path.production}")
    private String proPath;

    @Value("${path.archive}")
    private String arcPath;

    @Autowired
    public IampProperties iamp;

    @GetMapping("/hello")
    public String hello() {

        System.out.println(iamp.getUrl());
        return "hello";
    }



    @GetMapping("/path")
    public String path(ModelMap map) {
        String[] proPaths = proPath.split(",");
        String[] arcPaths = arcPath.split(",");
        map.put("pro", Arrays.toString(proPaths));
        map.put("arc", Arrays.toString(arcPaths));
        return "path";
    }

    @ResponseBody
    @GetMapping("/ajaxTest")
    public JSONObject ajaxTest() {
        JSONObject jo = new JSONObject();
        jo.put("id", 1);
        jo.put("name", "niuniu");
        return jo;
    }


    @ResponseBody
    @GetMapping(value = "/ajaxTextTest")
    public User ajaxTextTest() {
        System.out.println("===已接受到请求======");
        User jo = new User();
        jo.setId(1);
        jo.setName("bofei");
        return jo;
    }
    @ResponseBody
    @GetMapping(value = "/ajaxTextTest2")
    public String ajaxTextTest2() {
        System.out.println("===已接受到请求======");
        JSONObject jo = new JSONObject();
        jo.put("id", 1);
        jo.put("name", "bofei");
        return "aa";
    }

    @ResponseBody
    @GetMapping(value = "/ajaxTextTest3")
    public String ajaxTextTest3() {
        System.out.println("===已接受到请求======");
        JSONObject jo = new JSONObject();
        jo.put("id", 1);
        jo.put("name", "bofei");
        return JSON.toJSONString(jo);
    }

    @ResponseBody
    @GetMapping(value = "/ajaxTextTest4")
    public Map ajaxTextTest4() {
        System.out.println("===已接受到请求======");
        Map jo = new HashMap();
        jo.put("id", 1);
        jo.put("name", "bofei");
        return jo;
    }

}

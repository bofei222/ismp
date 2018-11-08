package com.nei.ismp.controller;

import com.nei.ismp.service.iampapi.IampApi;
import com.nei.ismp.service.iampapi.IampEnum;
import org.dom4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author bofei
 * @date 2018/11/6 9:40
 */
@RestController
public class IampApiTest {

    @Autowired
    private IampApi iampApi;

    @Autowired
    private RestTemplate restTemplate;

    /***********测试是否能收到 Post请求发来的参数,可以**************/
    @PostMapping("/testIamp")
    public String testIamp(String param1, String param2) {
        System.out.println(param1);
        System.out.println(param2);
        return "ok";
    }

    /***********测试是否能以Map为参数向Iamp发送请求，可以**********/
    @PostMapping("/testAampPost")
    public String testAampPost() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 参数类型必须为String，否则报错ClassCastException
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        //添加请求的参数
//        params.add("param1", "hello");             //必传
//        params.add("param2", "world");           //选传
        params.add("id", "9cff1be5-3572-4e82-ae6c-9392bd8a3409");
        params.add("status", "2");
        params.add("page_num", "1");
        params.add("page_size", "10");
        params.add("session_key", iampApi.logon());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(IampEnum.inquiry_task_items.getPath(), HttpMethod.POST, requestEntity, String.class);  //最后的参数需要用String.class  使用其他的会报错
        String body = response.getBody();

        return body;
    }

    /**********测试各方法的方法**/
    @GetMapping("/test")
    public String test() throws DocumentException {
//        inquiryTaskStatus("9cff1be5-3572-4e82-ae6c-9392bd8a3409");

//        String test = inquiryTaskItems("9cff1be5-3572-4e82-ae6c-9392bd8a3409", 2);

        String test = iampApi.createTask("波菲-1108-1", "192.168.1.55-share-cache-6TB/12W", "cd8ea62e-2430-45b4-a302-c21dc860480a",
                "a5e5403a-e3eb-4036-8f65-577f9cf040c9",
                Arrays.asList("30003文件/10001文件 - 副本-副本/10001个文件/武侠玄幻小说 - 副本 (4) - 副本/武侠玄幻03/《风云第一刀》/index.txt"));

        System.out.println("==========test" + test);
        return "test ok";
    }


}

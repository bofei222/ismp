package com.nei.ismp.iampapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nei.ismp.properties.IampProperties;
import org.dom4j.*;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bofei
 * @date 2018/11/6 9:40
 */
@RestController
public class IampApi {

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
        params.add("session_key", logon());
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

        String test = createTask("波菲-1107-3", "192.168.1.55-share-cache-6TB/12W", "cd8ea62e-2430-45b4-a302-c21dc860480a",
                "a5e5403a-e3eb-4036-8f65-577f9cf040c9",
                Arrays.asList("30003文件/10001文件 - 副本-副本/10001个文件/武侠玄幻小说 - 副本 (4) - 副本/武侠玄幻03/《风云第一刀》/index.txt"));

        System.out.println("==========test" + test);
        return "test ok";
    }

    /******查询任务状态************/
    public String inquiryTaskStatus(String id) throws DocumentException {
        Map<String,String> map = new HashMap();
        map.put("id", id);
        map.put("session_key", logon());

        ResponseEntity<String> entity = restTemplate.getForEntity(IampEnum.inquiry_task_status.getPath(),
                String.class, map);
        org.json.JSONObject taskStatus = XML.toJSONObject(entity.getBody());
        return taskStatus.toString();
    }

    /******查询任务清单**********/
    public String inquiryTaskItems(String id ,Integer status) {
        Map map = new HashMap();
//        JSONObject jo = new JSONObject();
        map.put("id", id);
        map.put("status", status);
        map.put("page_num", 1);
        map.put("page_size", 10);
        map.put("session_key", logon());
//        String s = restTemplate.postForObject(IampEnum.inquiry_task_items.getPath(), jo, String.class);
        String s = restTemplate.getForObject(IampEnum.inquiry_task_items2.getPath(), String.class, map);
        return s;
    }

    /*******创建任务*******************/
    public String createTask(String remark, String parameter_a, String parameter_b, String parameter_c,
                             List<String> filePaths) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 参数类型必须为String，否则报错ClassCastException
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("session_key", logon());
        params.add("types", "1");
        params.add("remark", remark);
        params.add("parameter_a", parameter_a);
        params.add("parameter_b", parameter_b);
        params.add("parameter_c", parameter_c);
//        params.add();("parameter_e", parameter_e);
//        params.add();("parameter_f", parameter_f);
//        params.add();("parameter_h", parameter_h);
        params.add("parameter_d", "/");
        params.add("parameter_g", "1");
        params.add("select_options", "6");
        params.add("select_count", String.valueOf(filePaths.size()));
        for (int i = 0; i < filePaths.size(); i++) {
            params.add("select_data_" + (i + 1), String.valueOf(filePaths.get(i)));
        }

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> entity = restTemplate.exchange(IampEnum.create_task.getPath(), HttpMethod.POST, requestEntity, String.class);
        System.out.println("==" + entity.getBody());
        org.json.JSONObject jsonObject = XML.toJSONObject(entity.getBody());
        return jsonObject.toString();
    }

    /******获取session_key********************/
    public String logon() {
        String sessionKeyXML = restTemplate.getForObject(IampEnum.logon.getPath(),
                String.class, IampProperties.username, IampProperties.webKey);
        Document document = null;
        String sessionKey = null;
        try {
            document = DocumentHelper.parseText(sessionKeyXML);
            Node node = document.selectSingleNode("/xml/session/key");
            sessionKey = node.getText();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return sessionKey;
    }
}

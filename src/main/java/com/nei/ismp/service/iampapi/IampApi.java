package com.nei.ismp.service.iampapi;

import com.nei.ismp.properties.IampProperties;
import com.nei.ismp.service.TaskService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bofei
 * @date 2018/11/8 14:22
 */
@Component
public class IampApi {

    @Autowired
    private RestTemplate restTemplate;

    /******查询任务状态************/
    public String inquiryTaskStatus(String id) {
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
        // map的值必须时String类型
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

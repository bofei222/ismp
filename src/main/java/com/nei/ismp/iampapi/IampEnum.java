package com.nei.ismp.iampapi;

import com.nei.ismp.properties.IampProperties;

import java.util.UUID;

/**
 * @author bofei
 * @date 2018/11/6 19:10
 * Iamp api 列表的枚举类
 */
public enum IampEnum {

    inquiry_task_status("/inquiry_task_status.websvc?id={id}&session_key={session_key}"),
    inquiry_task_items("/inquiry_task_items.websvc"), // 这个是Post，2是Get
    inquiry_task_items2("/inquiry_task_items.websvc?id={id}&status={status}&page_num={page_num}&page_size={page_size}&session_key={session_key}"),
    create_task("/create_task.websvc"),
    control("/control_task.websvc? Id=UUID-STRING&cmd=INTEGER&session_key=STRING"),
    logon("/logon.websvc?username={username}&password={password}");




    private String path;

    IampEnum(String path) {
        this.path = path;
    }

    public String getPath() {

        return IampProperties.url + path;
    }
}

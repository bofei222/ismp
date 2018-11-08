package com.nei.ismp.service;

import com.nei.ismp.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author bofei
 * @date 2018/11/8 17:26
 */
@Service
public class TaskService {

    @Resource
    private TaskMapper taskMapper;

    public String getTaskStatusById(String id) {
        System.out.println("执行查询任务。。。");
        return taskMapper.getTaskStatusById(id);
    }
}

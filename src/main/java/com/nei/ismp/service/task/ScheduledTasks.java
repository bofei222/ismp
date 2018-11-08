package com.nei.ismp.service.task;

import com.nei.ismp.service.TaskService;
import com.nei.ismp.service.iampapi.IampApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private IampApi iampApi;

    @Autowired
    private TaskService taskService;

//    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static ScheduledExecutorService timer = Executors.newScheduledThreadPool(100);

    /*********每50s扫描一次策略表**********/
    @Scheduled(fixedRate = 50000)
    public void reportCurentTime() throws InterruptedException {
        logger.info("扫描策略表，时间");
        // 符合条件就任务，
//        if (true) {
        // 调用 远程接口 创建写入任务
//        String taskUUID = iampApi.createTask("11", "12", "1", "12", Arrays.asList("1"));
        // 同时 创建另外一个线程,去时时查询任务状态

        queryStatus("a");
//        }
    }

    // 创建任务，周期性执行
    public void queryStatus(String id) {
//        System.out.println("周期性，用任务id查询，查询任务状态");
        MyTask myTask = new MyTask(id);
        // 用一个线程驱动任务
        ScheduledFuture<?> scheduledFuture =
                timer.scheduleAtFixedRate(myTask, 1, 30, TimeUnit.SECONDS);
//        scheduledFuture.cancel(true);


    }

    // 具体查询任务
    private class MyTask implements Runnable {
        private final String id;
        private String status = "1";

        public MyTask(String id) {
            this.id = id;
//            dateFormat = new SimpleDateFormat("HH:mm:ss");
        }

        @Override
        public void run() {
            // 根据查询到的状态，判断此周期任务是否继续
//            if (status.equals("1")) {
//                System.out.println("任务开始，当前时间：" + dateFormat.format(new Date()) + "===" + id);
            // 远程接口的封装
//                status = iampApi.inquiryTaskStatus(id);
            status = taskService.getTaskStatusById(id);
            logger.info("任务id:{},任务状态:{}", id, status);

            if (!status.equals("1")) {
                logger.info("任务已完成，停止任务");
                throw new RuntimeException();
            }

//            } else {
//                System.out.println("抛出异常，任务结束" + id);
//                throw new RuntimeException("任务结束");
//            }

        }
    }
}

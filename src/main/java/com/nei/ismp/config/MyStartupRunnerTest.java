package com.nei.ismp.config;

import com.nei.ismp.properties.PathProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author bofei
 * @Date 2018/11/13 15:10
 * @Description
 */
@Component
@Order(1)
public class MyStartupRunnerTest implements CommandLineRunner {
    private static final Logger LOGGRE = LoggerFactory.getLogger(CommandLineRunner.class);
    @Resource
    private PathProperties pathProperties;

    @Override
    public void run(String... strings) throws Exception {
            LOGGRE.info("磁盘子池{}", pathProperties.getArchive());
    }
}

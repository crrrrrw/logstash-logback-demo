package com.crw.elk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ImportResource("classpath:applicationContext-*.xml")
public class Bootstrap {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new SpringApplicationBuilder()
                .sources(Bootstrap.class)
                .web(false)
                .registerShutdownHook(false)
                .run(args);
        log.info("main print log, uuid:{}", UUID.randomUUID().toString());

        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);

        // register Message as shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                log.error("========>service stop<===========");
            }
        }));

        try {
            closeLatch.await();
        } catch (InterruptedException e) {
            log.error("程序中断异常退出");
            e.printStackTrace();
        }
    }

}

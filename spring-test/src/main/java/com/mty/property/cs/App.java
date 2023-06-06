package com.mty.property.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@ServletComponentScan(basePackages = "com.mty.property")
//@MapperScan(basePackages = "com.mty.property.mapper")
//@EnableAsync
//@EnableScheduling
//@EnableMethodCache(basePackages = "com.mty.property")
//@EnableCreateCacheAnnotation
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        System.out.println("=======================启动成功=====================");
    }
}

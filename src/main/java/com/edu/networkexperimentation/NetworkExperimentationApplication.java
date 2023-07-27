package com.edu.networkexperimentation;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMPP
@MapperScan("com.edu.networkexperimentation.mapper")
public class NetworkExperimentationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NetworkExperimentationApplication.class, args);
    }

}

package be.pxl.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
//import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Config Service
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }
}

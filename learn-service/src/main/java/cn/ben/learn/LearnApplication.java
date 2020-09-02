package cn.ben.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"cn.ben.learn"},excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE))
public class LearnApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearnApplication.class,args);
    }
}

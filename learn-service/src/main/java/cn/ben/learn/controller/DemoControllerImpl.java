package cn.ben.learn.controller;

import cn.ben.learn.DemoController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoControllerImpl implements DemoController {


    @Override
    public String testDemo() {
        return "hello spring-boot";
    }
}

package cn.ben.learn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/demo")
public interface DemoController {

    @GetMapping("/test")
    public String testDemo();
}

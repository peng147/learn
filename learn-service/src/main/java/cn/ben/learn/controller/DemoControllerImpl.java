package cn.ben.learn.controller;

import cn.ben.learn.DemoController;
import cn.ben.learn.manager.PDFManager;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DemoControllerImpl implements DemoController {

    @Resource
    private PDFManager pdfManager;


    @Override
    public String testDemo() {
        return "hello spring-boot";
    }

    @Override
    public void testPDFManager() {
        pdfManager.testPDF();
    }
}

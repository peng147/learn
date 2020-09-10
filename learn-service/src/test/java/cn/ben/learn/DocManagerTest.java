package cn.ben.learn;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
public class DocManagerTest {


    @Test
    public void testDocManager(){

    }


    /**
     * freemarker模板方法
     * @param templateTxt 模板文本
     * @param map  模板参数
     * @return
     * @throws Exception
     */
    public static String getFreeMarkerText(String templateTxt, Map<String, Object> map) throws Exception {
        String result = null;
        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        try {
            StringTemplateLoader e = new StringTemplateLoader();
            e.putTemplate("t", templateTxt);
            config.setTemplateLoader(e);
            config.setDefaultEncoding("UTF-8");
            Template template = config.getTemplate("t", "UTF-8");
            StringWriter out = new StringWriter();
            template.process(map, out);
            result = out.toString();
            return result;
        } catch (IOException iex) {
            throw new Exception("获取freemark模版出错", iex);
        } catch (TemplateException ex) {
            throw new Exception("freemark模版处理异常", ex);
        }
    }
}

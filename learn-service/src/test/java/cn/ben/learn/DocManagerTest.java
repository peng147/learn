package cn.ben.learn;

import cn.ben.learn.manager.PDFManager;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class DocManagerTest {


    @Test
    public void testDocManager() throws Exception {
        String path = this.getClass().getResource("/").getPath();
        String name = "/"+ UUID.randomUUID().toString()+".doc";
        boolean w = false;
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("name","(中文签名)");
        params.put("career","软件开发");
        params.put("blog","微博链接 www.qidian.com");
        String content = getFreeMarkerText(htmlContent(),params);
        byte b[] = content.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
       /* POIFSFileSystem poifs = new POIFSFileSystem();
        DirectoryEntry directory = poifs.getRoot();
        DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);*/
        FileOutputStream ostream = new FileOutputStream(path+ name);
        /*poifs.writeFilesystem(ostream);*/
        inputStreamToWord(bais,ostream);
        bais.close();
        ostream.close();

    }

    /**
     * 把is写入到对应的word输出流os中
     * 不考虑异常的捕获，直接抛出
     * @param is
     * @param os
     * @throws IOException
     */
    private void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem();
        //对应于org.apache.poi.hdf.extractor.WordDocument
        fs.createDocument(is, "WordDocument");
        fs.writeFilesystem(os);
        os.close();
        is.close();
    }

    /**
     * 获取html内容
     * @return
     */
    private String htmlContent() {
        String result = "";
        try {
            URL url = PDFManager.class.getClassLoader().getResource("template.html");
            FileInputStream fileInputStream = new FileInputStream(url.getPath());
            int len = 0;
            byte[] array = new byte[1024];
            StringBuffer stringBuffer = new StringBuffer();
            while ((len = fileInputStream.read(array)) != -1) {
                stringBuffer.append(new String(array, 0, len));
            }
            result = stringBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * freemarker模板方法
     * @param templateTxt 模板文本
     * @param map  模板参数
     * @return
     * @throws Exception
     */
    public  String getFreeMarkerText(String templateTxt, Map<String, Object> map) throws Exception {
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

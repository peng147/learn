package cn.ben.learn;

import cn.ben.learn.provide.ChinaFontProvide;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
public class PDFManagerTest {



    @Test
    public void testPDF(){
        String path = this.getClass().getResource("/").getPath();
        String name = "/"+UUID.randomUUID().toString()+".pdf";
        try{
            // 1. new Document
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path + name));
            // 2. 打开document
            document.open();
            //2.open document
            document.open();
           /* //3. 设置字体
            XMLWorkerFontProvider xmlWorkerFontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            xmlWorkerFontProvider.register(getContextPath()+FONT_PATH);
            BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);*/
            //4. 设置模板内容
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("name","ben-sir(中文)");
            params.put("career","软件开发");
            params.put("blog","http://www.andyqian.com");
            String content = getFreeMarkerText(htmlContent(),params);
            //4. 文件
            InputStream inputStream = new ByteArrayInputStream(content.getBytes("utf-8"));
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,inputStream, Charset.forName("UTF-8"),new ChinaFontProvide());
            //3. close document
            document.close();
        }catch (DocumentException e) {
            e.printStackTrace();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(path);
    }

    /**
     * 获取资源的路径
     * @return
     */
    private String path(){
        String path = this.getClass().getResource("/").getPath();
        return path;
    }

    @Test
    public void testHTMLPDF(){

    }

    private File getPDFFile(String htmlContent,File file){
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        PdfWriter writer = null;
        try {
            // 1. 获取生成pdf的html内容
            inputStream= new ByteArrayInputStream(htmlContent.getBytes("utf-8"));
            outputStream = new FileOutputStream(file);
            Document document = new Document();
            writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            // 2. 添加字体
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register(getFontPath());
            // 3. 设置编码
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream, Charset.forName("UTF-8"),fontImp);
            // 4. 关闭,(不关闭则会生成无效pdf)
            document.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(writer!=null){
                    writer.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 应用场景:
     * 1.在windows下,使用Thread.currentThread()获取路径时,出现空对象，导致不能使用
     * 2.在linux下,使用PdfUtils.class获取路径为null,
     * 获取字体路径
     * @return
     */
    private static String getFontPath(){
        String path="";
        // 1.
        ClassLoader classLoader= Thread.currentThread().getContextClassLoader();
        URL url = (classLoader==null)?null:classLoader.getResource("/");
        String threadCurrentPath = (url==null)?"":url.getPath();
        // 2. 如果线程获取为null,则使用当前PdfUtils.class加载路径
        if(StringUtils.isEmpty(threadCurrentPath)){
            path = ClassLoader.class.getResource("/").getPath();
        }
        // 3.拼接字体路径
        StringBuffer stringBuffer = new StringBuffer(path);
        stringBuffer.append("/fonts/SIMKAI.TTF");
        path = stringBuffer.toString();
        log.info("getFontPath threadCurrentPath: {}  path: {}",threadCurrentPath,path);
        return path;
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

    private static final String FONT_PATH="/fonts/SIMKAI.TTF";
    private static final String HTML_PATH="/html/feemarker.html";


    /**
     * 获取上下文路径
     * @return
     */
    private String getContextPath(){
        String path = this.getClass().getResource("/").getPath();
        return path;
    }

    /**
     * 获取html内容
     * @return
     */
    private String htmlContent(){
        String result = "";
        try {
            FileInputStream fileInputStream = new FileInputStream("/Users/ben.liu/IdeaProjects/ben/learn/learn-service/src/main/resources/template.html");
            int len=0;
            byte[] array = new byte[1024];
            StringBuffer stringBuffer = new StringBuffer();
            while((len=fileInputStream.read(array))!=-1){
                stringBuffer.append(new String(array,0,len));
            }
            result = stringBuffer.toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}

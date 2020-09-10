package cn.ben.learn.manager;

import cn.ben.learn.provide.ChinaFontProvide;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class PDFManager {


    public void testPDF(){
        String path = this.getClass().getResource("/").getPath();
        String name = "/"+ UUID.randomUUID().toString()+".pdf";
        try{
            // 1. new Document
            Rectangle rectPageSize = new Rectangle(PageSize.A4);// A4纸张
            Document document = new Document(rectPageSize, 40, 40, 40, 40);// 上、下、左、右间距

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path + name));
            // 2. 打开document
            document.open();
            //2.open document
            document.open();
            //3. 设置字体
            XMLWorkerFontProvider xmlWorkerFontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            /* xmlWorkerFontProvider.register(getContextPath()+FONT_PATH);*/
            /*    BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);*/
            //4. 设置模板内容
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("name","(中文签名)");
            params.put("career","软件开发");
            params.put("blog","微博链接 www.qidian.com");
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

    /**
     * 获取html内容
     * @return
     */
    private String htmlContent(){
        String result = "";
        try {
            URL url = PDFManager.class.getClassLoader().getResource("template.html");
            FileInputStream fileInputStream = new FileInputStream(url.getPath());
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


    public static void main(String[] args) throws IOException {
        URL url = PDFManager.class.getClassLoader().getResource("template.html");

        BufferedReader br = new BufferedReader(new InputStreamReader(
                url.openStream()));

        StringBuilder contentHolder = new StringBuilder();

        String lineContent = null;

        while ((lineContent = br.readLine()) != null)
        {
            contentHolder.append(lineContent);
        }

        br.close();

        System.out.println("content=" + contentHolder);


    }

    public void showContent(URL url) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                url.openStream()));

        StringBuilder contentHolder = new StringBuilder();

        String lineContent = null;

        while ((lineContent = br.readLine()) != null)
        {
            contentHolder.append(lineContent);
        }

        br.close();

        System.out.println("content=" + contentHolder);

    }

}

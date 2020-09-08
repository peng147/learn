package cn.ben.learn;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

public class PDFTestManager {



    @Test
    public void testPDF(){
        String path = this.getClass().getResource("/").getPath();
        String name = "/"+UUID.randomUUID().toString()+".pdf";
        try{
            // 1. new Document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path+name));
            // 2. 打开document
            document.open();
            // 3. 添加内容
            document.add(new Paragraph("test pdf！"));
            // 4. 关闭 (如果未关闭则会生成无效的pdf文件)
            document.close();
        }catch (DocumentException e) {
            e.printStackTrace();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        System.out.println(path);
    }
}

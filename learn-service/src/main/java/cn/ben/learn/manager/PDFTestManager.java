package cn.ben.learn.manager;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.html.dom.HTMLDocumentImpl;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
@Slf4j
public class PDFTestManager {


    /**
     * 生成pdf文件
     */
    public static void testCreatePdf(){
        try{
            // 1. new Document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("/Users/ben.liu/Desktop/test/1.pdf"));
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
    }

    public static void main(String[] args) {

    }


}

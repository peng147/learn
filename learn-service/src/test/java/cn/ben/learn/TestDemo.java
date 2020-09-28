package cn.ben.learn;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDemo {

    @Test
    public void test(){
        Boolean aBoolean = test1("/Users/ben.liu/Desktop/test/3.docx", "/Users/ben.liu/Desktop/test/hello.pdf");
        System.out.println(aBoolean);
    }

    public Boolean test1(String sourceFile, String destFile) {
        try {

            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                // 找不到源文件, 则返回false
                return false;
            }
            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            //如果目标文件存在，则删除
            if (outputFile.exists()) {
                outputFile.delete();
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
            connection.connect();
            //用于测试openOffice连接时间
            System.out.println("连接时间:" + df.format(new Date()));
            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(
                    connection);
            converter.convert(inputFile, outputFile);
            //测试word转PDF的转换时间
            System.out.println("转换时间:" + df.format(new Date()));
            connection.disconnect();
            return true;
        } catch (ConnectException e) {
            e.printStackTrace();
            System.err.println("openOffice连接失败！请检查IP,端口");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}

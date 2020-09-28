package cn.ben.learn;

import fr.opensagres.xdocreport.utils.StringEscapeUtils;
import org.apache.commons.io.FileUtils;
import org.docx4j.convert.in.xhtml.FormattingOption;
import org.docx4j.convert.in.xhtml.XHTMLImporter;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.wml.RFonts;

import java.io.File;

public class DocxManagerTest {

    public static void main(String[] args) throws Exception {
        String inputfilepath = "C:\\Users\\Administrator\\Desktop\\test.html";
        String baseURL = "C:\\Users\\Administrator\\Desktop";

        String stringFromFile = FileUtils.readFileToString(new File(inputfilepath), "UTF-8");

        String unescaped = stringFromFile;
        if (stringFromFile.contains("&lt;/") ) {
           /* unescaped = StringEscapeUtils.unescapeHtml(stringFromFile);*/
        }
        // 设置字体映射
        RFonts rfonts = Context.getWmlObjectFactory().createRFonts();
        rfonts.setAscii("Century Gothic");
        XHTMLImporterImpl.addFontMapping("Century Gothic", rfonts);

        // 创建一个空的docx对象
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        XHTMLImporter importer = new XHTMLImporterImpl(wordMLPackage);
        importer.setTableFormatting(FormattingOption.IGNORE_CLASS);
        importer.setParagraphFormatting(FormattingOption.IGNORE_CLASS);

        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
        wordMLPackage.getMainDocumentPart().addTargetPart(ndp);
        ndp.unmarshalDefaultNumbering();

        // 转换XHTML，并将其添加到我们制作的空docx中
        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);

        XHTMLImporter.setHyperlinkStyle("Hyperlink");
        wordMLPackage.getMainDocumentPart().getContent().addAll(
                XHTMLImporter.convert(unescaped, baseURL));
        wordMLPackage.save(new java.io.File("C:\\Users\\Administrator\\Desktop\\test.docx"));
    }
}

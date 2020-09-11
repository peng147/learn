package cn.ben.learn.provide;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;

import java.awt.*;

public class CNFontProvide implements IFontProvider {
    @Override
    public Font getFont(String s, String s1, float v, int i, Color color) {
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Font   FontChinese   =   new   com.lowagie.text.Font(bfChinese,   12,   Font.NORMAL);
        return FontChinese;
    }
}

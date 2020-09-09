package cn.ben.learn.provide;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.stereotype.Service;

import static com.itextpdf.text.Font.UNDEFINED;

@Service
public class ChinaFontProvide implements FontProvider {
    @Override
    public boolean isRegistered(String fontname) {
        return false;
    }

    @Override
    public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Font FontChinese = new Font(bfChinese, 8, Font.getStyleValue(Font.FontStyle.ITALIC.getValue()));
        return FontChinese;
    }
}

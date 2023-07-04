package com.lon.lon_v3.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.lon.lon_v3.entity.vo.UserExcel;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


public class PdfUtils {

    /**
     * 字体存放的跟路径，默认为'C:\Windows\Fonts\'
     */
    private static final String FONT_PATH = "C:\\Windows\\Fonts\\";

    /**
     * 纸张大小
     */
    private static Rectangle RECTANGLE = PageSize.A4;

    /**
     * 设置字体默认值
     *
     * @throws DocumentException
     * @throws IOException
     */
    private static BaseFont createBaseFont(String fontName) throws DocumentException, IOException {
        // 默认为宋体
        if (fontName == null) {
            fontName = "simsun.ttc";
        }
        String fontPre = fontName.substring(fontName.lastIndexOf(".") + 1);
        if (fontPre.equals("ttc")) {
            // ttc格式的字体需要加上后缀
            fontName = fontName + ",0";
        }
        String font = FONT_PATH + fontName;
        return BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
    }

    /**
     * 设置字体
     *
     * @return
     */
    public static Font setFont() {
        return setFont(null, null, null, null);
    }

    public static Font setFont(Integer fontSize) {
        return setFont(null, fontSize, null, null);
    }

    public static Font setFont(Integer fontSize, BaseColor fontColor) {
        return setFont(null, fontSize, null, fontColor);
    }

    /**
     * @param fontName  字体名称 默认宋体
     * @param fontSize  字体大小 默认12号
     * @param fontStyle 字体样式
     * @param fontColor 字体颜色 默认黑色
     * @return
     */
    public static Font setFont(String fontName, Integer fontSize, Integer fontStyle, BaseColor fontColor) {
        try {
            BaseFont baseFont = createBaseFont(fontName);
            Font font = new Font(baseFont);
            if (fontSize != null) {
                font.setSize(fontSize);
            }
            if (fontStyle != null) {
                font.setStyle(fontStyle);
            }
            if (fontColor != null) {
                font.setColor(fontColor);
            }
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("设置字体失败！");
        }
    }

    /**
     * 创建pdf文档
     *
     * @param response
     * @param fileName
     * @return
     */
    public static Document createDocument(HttpServletResponse response, String fileName) {
        try {
            response.reset();
            response.setHeader("Content-Type", "application/pdf-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置大小
        RECTANGLE = PageSize.A4;
        return new Document(RECTANGLE, 50, 50, 80, 50);
    }

    public static Document createDocument(String fileName) {
        String filePath = "E:\\file\\pdf\\";
        // 设置大小
        RECTANGLE = PageSize.A4;
        return new Document(RECTANGLE, 50, 50, 80, 50);
    }

    /**
     * 绘制标题
     *
     * @param font
     * @param titleName
     * @return
     */
    public static Paragraph setParagraph(Font font, String titleName) {
        Paragraph paragraph = new Paragraph(titleName, font);
        //设置文字居中
        paragraph.setAlignment(Element.ALIGN_CENTER);
        //行间距
        paragraph.setLeading(5f);
        //设置段落上空白
        paragraph.setSpacingBefore(-20f);
        //设置段落下空白
        paragraph.setSpacingAfter(15f);
        return paragraph;
    }

    /**
     * 设置
     * 表格内容
     *
     * @param headFont
     * @param textFont
     * @param title
     * @param list
     * @return
     */
    public static PdfPTable setTable(Font headFont, Font textFont, String[] title, List<UserExcel> list) {
        //四列
        PdfPTable table = createTable(new float[]{120, 120, 120, 120,120,120});

        for (String head : title) {
            table.addCell(createCell(head, headFont));
        }
        for (UserExcel userExcel : list) {
            table.addCell(createCell(userExcel.getId().toString(), textFont));
            table.addCell(createCell(userExcel.getUno(), textFont));
            table.addCell(createCell(userExcel.getName(), textFont));
            table.addCell(createCell(userExcel.getGender().toString(), textFont));
            table.addCell(createCell(userExcel.getDept().toString(), textFont));
            table.addCell(createCell(userExcel.getHome() == null ? "" : userExcel.getHome(), textFont));
        }

        return table;
    }

    private static PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            // 设置表格大小
            table.setTotalWidth(RECTANGLE.getWidth() - 100);

            table.setLockedWidth(true);
            // 居中
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 边框
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    private static PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        // 水平、垂直居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
}



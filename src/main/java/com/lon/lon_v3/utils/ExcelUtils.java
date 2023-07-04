package com.lon.lon_v3.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.lon.lon_v3.Strategy.CustomColumnWidthStyleStrategy;
import com.lon.lon_v3.entity.User;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.utils
 * @className: ExcelUtils
 * @author: LONZT
 * @description: TODO
 * @date: 2023/6/16 14:31
 * @version: 1.0
 */
public class ExcelUtils {


    public static void exportExecl(HttpServletResponse response,String fileName, List list, Class cls) throws IOException {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
//        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short)14);

        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

//      设置边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(false);
        // 背景绿色
        // contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        contentWriteFont.setFontName("宋体");
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        // 设置响应头信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName1 = URLEncoder.encode(fileName+".xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName1);
        try {
            // 调用EasyExcel方法进行导出
            EasyExcel.write(response.getOutputStream(), cls)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(new CustomColumnWidthStyleStrategy())
//                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(20)) // 简单的列宽策略，列宽20
                    .registerWriteHandler(new SimpleRowHeightStyleStrategy((short)25,(short)20)) // 简单的行高策略：头行高，内容行高
                    .sheet("用户信息")
                    .doWrite(list);
        }catch (Exception e){
            e.printStackTrace();
        };


    }

    public static void exportExeclToPDF(HttpServletResponse response,String fileName, List list, Class cls) throws IOException {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
//        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short)14);

        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

//      设置边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(false);
        // 背景绿色
        // contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        contentWriteFont.setFontName("宋体");
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);


        // 调用EasyExcel方法进行导出
        EasyExcel.write(fileName+".xlsx", User.class)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new CustomColumnWidthStyleStrategy())
//                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(20)) // 简单的列宽策略，列宽20
                .registerWriteHandler(new SimpleRowHeightStyleStrategy((short)25,(short)20)) // 简单的行高策略：头行高，内容行高
                .sheet("用户信息")
                .doWrite(list);

        FileUtils.download(response,fileName+".pdf",1);

    }






}

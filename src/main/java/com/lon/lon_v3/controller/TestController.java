package com.lon.lon_v3.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lon.lon_v3.comomon.Result;
import com.lon.lon_v3.entity.User;
import com.lon.lon_v3.entity.vo.UserExcel;
import com.lon.lon_v3.mapper.UserMapper;
import com.lon.lon_v3.mapstruct.UserConvertUserExcel;
import com.lon.lon_v3.service.DeptService;
import com.lon.lon_v3.service.UserService;
import com.lon.lon_v3.utils.CollectionsUtils;
import com.lon.lon_v3.utils.PdfUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.alibaba.druid.util.JdbcSqlStatUtils.getData;
import static com.itextpdf.text.BaseColor.*;

@Api("test")
@RestController
@Validated
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    final UserService userService;

    final UserMapper userMapper;

    final DeptService deptService;

    private static String filePath= "E:\\file\\excel\\";


    @GetMapping (value = "/hai")
    public String HelloWorld(){
        return "Hello World";
    }

    @RequestMapping("/date")
    protected LocalTime date(){
        return LocalTime.now();
    }



    @ApiOperation("根据姓名查询")
    @ApiImplicitParam(name = "name",value = "姓名",dataType = "String")
    @GetMapping (value = "/users")
    public Result users(@NotEmpty(message = "姓名不能为空")String name){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        Page page = new Page<>(1,20);
        Page user = userService.findUserByName(name);
        return Result.success(user);
    }



    @GetMapping (value = "/excel")
    public Result longTest(@NotEmpty(message = "学号不能为空")@Size(message = "学号格式错误",min = 1,max = 12) String uno){

        QueryWrapper queryWrapper =new QueryWrapper<>();
        queryWrapper.likeRight("uno",uno);
        List<User> list = userMapper.selectList(queryWrapper);

//        //        向Excel中写入数据 也可以通过 head(Class<?>) 指定数据模板
        Map map = deptService.getDeptMap();

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            user.setDept((String) map.get(Integer.valueOf(user.getDept())));
            list.set(i, user);
        }
        List<UserExcel> userExcels = UserConvertUserExcel.INSTANCE.toConvertExcel(list);

        String fileName = "用户信息"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".xlsx";

        EasyExcel.write(filePath+fileName, UserExcel.class)
                .sheet("用户信息")
                .doWrite(userExcels);


        return Result.success();
    }

    @RequestMapping("getUserList")
    public Result getUserList(@RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "1")Integer pageNum, HttpServletRequest request){
//        System.out.println(request.getHeader("Authorization"));
        Page<User> userPage = new Page<>(pageNum,pageSize);
        Page<User> page = userMapper.selectPage(userPage,null);
        return Result.success("成功", page);
    }

    @RequestMapping("getUserRank")
    public Result getUserList(String uno){
//        System.out.println(request.getHeader("Authorization"));

        User user = userMapper.findUserRankByName(uno);
        return Result.success("成功", user);
    }


    @GetMapping("/pdf")
    public void downloadPDF(HttpServletResponse response) {
        List<UserExcel> cachedDataList = new ArrayList<>();
        String filename = "E:\\file\\excel\\userExcel1.xlsx";
        // 创建ExcelReaderBuilder对象
        ExcelReaderBuilder readerBuilder = EasyExcel.read();
        // 获取文件对象
        readerBuilder.file(filename);
        // 指定映射的数据模板
        readerBuilder.head(UserExcel.class);
        // 指定sheet
        readerBuilder.sheet(0);
        // 自动关闭输入流
        readerBuilder.autoCloseStream(true);
        // 设置Excel文件格式
        readerBuilder.excelType(ExcelTypeEnum.XLSX);
        // 注册监听器进行数据的解析
        readerBuilder.registerReadListener(new AnalysisEventListener() {
            // 每解析一行数据,该方法会被调用一次
            @Override
            public void invoke(Object demoData, AnalysisContext analysisContext) {
                // 如果没有指定数据模板, 解析的数据会封装成 LinkedHashMap返回
                // demoData instanceof LinkedHashMap 返回 true
//                System.out.println("解析数据为:" + demoData.toString());

                cachedDataList.add((UserExcel) demoData);
                System.out.println( demoData.toString());
            }

            // 全部解析完成被调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                Map<String,String> map = deptService.getDeptMap();
//
//                System.out.println(cachedDataList);
//                for (int i = 0; i < cachedDataList.size(); i++) {
//                    UserExcel user = cachedDataList.get(i);
//                    user.setDept(CollectionsUtils.getKeysByStream(map, user.getDept()).stream().toArray()[0].toString());
//                    cachedDataList.set(i, user);
//                }
//                System.out.println(cachedDataList);
                System.out.println("解析完成...");
                // 可以将解析的数据保存到数据库
            }
        });
        readerBuilder.doReadAll();

        try (BufferedOutputStream os = new BufferedOutputStream(response.getOutputStream())) {
            // 1.设置输出的文件名称
            String fileName = "人员信息表.pdf";

            // 2.创建pdf文档，并且设置纸张大小为A4
            Document document = PdfUtils.createDocument(response,fileName);
//            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            PdfWriter.getInstance(document, os);

            // 3.打开文档
            document.open();

            // 4.设置标题
            String titleName = "人员信息表";
            // 设置字体样式：黑体 20号 加粗 红色
            Font titleFont = PdfUtils.setFont("simhei.ttf", 20, Font.BOLD, RED);
            Paragraph paragraph = PdfUtils.setParagraph(titleFont, titleName);

            // 5.设置表格
            // 定义列名
            String[] title = {"序号","学号", "姓名", "性别","部门", "地址"};
            // 获取列表数据
            // 设置表头字体样式：黑体 14号 加粗 黑色
            // 设置正文字体样式：12号
            Font headFont = PdfUtils.setFont("simhei.ttf", 12, Font.BOLD, BLACK);
            Font textFont = PdfUtils.setFont(12);
            // 模拟获取数据
            PdfPTable table = PdfUtils.setTable(headFont, textFont, title, cachedDataList);
            // 8.填充内容
            document.add(paragraph);
            document.add(table);

            // 关闭资源
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

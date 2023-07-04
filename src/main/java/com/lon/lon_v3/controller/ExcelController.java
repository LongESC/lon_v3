package com.lon.lon_v3.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lon.lon_v3.comomon.Result;
import com.lon.lon_v3.entity.User;
import com.lon.lon_v3.mapper.UserMapper;
import com.lon.lon_v3.service.DeptService;
import com.lon.lon_v3.service.UserService;
import com.lon.lon_v3.utils.ExcelUtils;
import com.lon.lon_v3.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.controller
 * @className: ExcelController
 * @author: LONZT
 * @description: TODO
 * @date: 2023/6/14 17:11
 * @version: 1.0
 */

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController {

    final UserService userService;

    final DeptService deptService;

    final UserMapper userMapper;

    private static String filePath= "E:\\file\\excel\\";

    @RequestMapping("/getFilesList")
    public Result getFilesList(){

        return Result.success(FileUtils.traverseFolder(filePath));

    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        QueryWrapper queryWrapper =new QueryWrapper<>();
        queryWrapper.likeRight("uno","7101");
        List<User> list = userMapper.selectList(queryWrapper);

        //        向Excel中写入数据 也可以通过 head(Class<?>) 指定数据模板
        Map map = deptService.getDeptMap();
        System.out.println(map);

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            user.setDept((String) map.get(Integer.valueOf(user.getDept())));
            list.set(i, user);
        }

        ExcelUtils.exportExecl(response,"user",list, User.class);


    }



    @GetMapping("/exportPDF")
    public void exportPDF(HttpServletResponse response) throws IOException {

        QueryWrapper queryWrapper =new QueryWrapper<>();
        queryWrapper.likeRight("uno","7101");
        List<User> list = userMapper.selectList(queryWrapper);

        //        向Excel中写入数据 也可以通过 head(Class<?>) 指定数据模板
        Map map = deptService.getDeptMap();
        System.out.println(map);

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            user.setDept((String) map.get(Integer.valueOf(user.getDept())));
            list.set(i, user);
        }
        String fileName = filePath+"用户信息"+new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        ExcelUtils.exportExeclToPDF(response,fileName,list, User.class);


    }


}

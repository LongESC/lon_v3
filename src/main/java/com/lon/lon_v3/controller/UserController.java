package com.lon.lon_v3.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.lon_v3.comomon.Result;
import com.lon.lon_v3.entity.User;
import com.lon.lon_v3.entity.vo.UserExcel;
import com.lon.lon_v3.mapper.UserMapper;
import com.lon.lon_v3.mapstruct.UserConvertUserExcel;
import com.lon.lon_v3.service.DeptService;
import com.lon.lon_v3.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.controller
 * @className: UserController
 * @author: LONZT
 * @description: TODO
 * @date: 2023/6/19 15:31
 * @version: 1.0
 */

@RestController
@Validated
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    final UserMapper userMapper;

    final DeptService deptService;

    private static String filePath= "E:\\file\\excel\\";


    @GetMapping (value = "/byName")
    public Result findUserByName(@NotEmpty(message = "姓名不能为空")@RequestParam("name") String name){

        Page user = userService.findUserByName(name);
        return Result.success(user);
    }

    @GetMapping (value = "/byUno")
    public Result findUserByUno(@NotEmpty(message = "用户学工号不能数为空")@RequestParam("uno") String uno){
        Page page = new Page<>(1,20);
        Page user = userService.findUserByUno(page,uno);
        Page<UserExcel> userExcelPage = UserConvertUserExcel.INSTANCE.toConvertExcel(user);
        return Result.success(userExcelPage);
    }

    @RequestMapping(value = "/find")
    public Result find(@NotEmpty(message = "用户学工号不能数为空") String tno, String idno){
        Map map= new HashMap();
        map.put("tno",tno);
        map.put("idno",idno);

        return Result.success(map);
    }



}

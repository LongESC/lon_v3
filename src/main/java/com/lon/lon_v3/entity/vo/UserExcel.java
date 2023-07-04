package com.lon.lon_v3.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import com.lon.lon_v3.converter.DeptConverter;
import com.lon.lon_v3.converter.SexConverter;
import com.lon.lon_v3.entity.Dept;
import com.lon.lon_v3.entity.Rank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.entity.vo
 * @className: UserExcel
 * @author: LONZT
 * @description: TODO
 * @date: 2023/6/19 11:37
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExcel implements Serializable, TransPojo {

        @ExcelProperty(value = "序号")
        private Integer id;
        @ExcelProperty(value = "学工号")
        private String uno;
        @ExcelProperty(value = "角色")
        private Integer role;
        @ExcelProperty(value = "性别",converter = SexConverter.class)
        private Integer gender;
        @ExcelProperty(value = "姓名")
        private String name;
        @Trans(type = TransType.SIMPLE,target = Dept.class,fields = "deptName")
        @ExcelProperty(value = "部门")
        private String dept;
        @ExcelProperty(value = "住址")
        private String home;


}

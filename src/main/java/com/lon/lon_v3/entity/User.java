package com.lon.lon_v3.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.entity
 * @className: User
 * @author: LONZT
 * @description: TODO
 * @date: 2023/5/9 14:41
 * @version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "md_user")
public class User implements Serializable,TransPojo{

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "uno")
    private String uno;
    @TableField(value = "role")
    private Integer role;
    @TableField(value = "gender")
    private Integer gender;

    @TableField(value = "name")
    private String name;
    @Trans(type = TransType.SIMPLE,target = Dept.class,fields = "deptName")
    @TableField(value = "dept")
    private String dept;

    @TableField(value = "home")
    private String home;

    @TableField(value = "clsName")
    private String clsName;

    @TableField(value = "tchName")
    private String tchName;

    @TableField(exist = false)
    private Rank rank;


}

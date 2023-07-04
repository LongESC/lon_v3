package com.lon.lon_v3.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.vo.TransPojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lon
 * @since 2023-03-09
 */
@TableName("dic_dept")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept implements Serializable, TransPojo {


    @TableId(value = "id")
    private Integer id;
    @TableField("pid")
    private Integer pid;
    @TableField("name")
    private String deptName;


}

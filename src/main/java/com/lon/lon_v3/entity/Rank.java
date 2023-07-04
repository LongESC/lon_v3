package com.lon.lon_v3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.util.List;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.entity
 * @className: Rank
 * @author: LONZT
 * @description: TODO
 * @date: 2023/5/29 9:23
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_rank")
public class Rank {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "cate")
    private Integer cate;
    @TableField(value = "role")
    private Integer role;
    @TableField(value = "dept")
    private Integer dept;
    @TableField(value = "sc;")
    private String sc;
    @TableField(value = "ct")
    private String ct;

   private List<User> userList;
}

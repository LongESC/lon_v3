package com.lon.lon_v3.mapper;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.mapper
 * @className: SqlMapper
 * @author: LONZT
 * @description: TODO
 * @date: 2023/5/9 10:50
 * @version: 1.0
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.lon_v3.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Admin
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    Page<User> findUserByName(Page page,@Param("name") String name);

    Page<User> findUserByUno(Page page,@Param("uno") String uno);

    User findUserRankByName(@Param("uno") String uno);
    void dynamicsInsert(@Param("paramSQL") String sql);

    void dynamicsUpdate(@Param("paramSQL") String sql);

    void dynamicsDelete(@Param("paramSQL") String sql);
}


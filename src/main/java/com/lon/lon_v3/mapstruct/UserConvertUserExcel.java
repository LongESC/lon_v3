package com.lon.lon_v3.mapstruct;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.lon_v3.entity.User;
import com.lon.lon_v3.entity.vo.UserExcel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.mapstruct
 * @className: UserConvertUserExcel
 * @author: LONZT
 * @description: TODO
 * @date: 2023/6/19 11:42
 * @version: 1.0
 */
@Mapper(componentModel = "spring", uses = UserConvertUserExcel.class)
public interface UserConvertUserExcel {

    UserConvertUserExcel INSTANCE = Mappers.getMapper(UserConvertUserExcel.class);

    /**
     * 字段数量类型数量相同，利用工具BeanUtils也可以实现类似效果
     * @param source
     * @return
     */
//    UserExcel toConvertVO1(User source);
//    User fromConvertEntity1(UserVO1 userVO1);

    /**
     * 字段数量类型相同,数量少：仅能让多的转换成少的，故没有fromConvertEntity2
     * @param source
     * @return
     */
    UserExcel toConvertExcel(User source);
    List<UserExcel> toConvertExcel(List<User> source);
    Page<UserExcel> toConvertExcel(Page<User> source);

}

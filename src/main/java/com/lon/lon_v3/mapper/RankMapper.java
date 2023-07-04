package com.lon.lon_v3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lon.lon_v3.entity.Rank;
import com.lon.lon_v3.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.mapper
 * @className: RankMapper
 * @author: LONZT
 * @description: TODO
 * @date: 2023/5/29 9:28
 * @version: 1.0
 */
@Mapper
public interface RankMapper extends BaseMapper<Rank> {

    Rank findUsersRank(@Param("id") Integer id,@Param("role") Integer role);
}

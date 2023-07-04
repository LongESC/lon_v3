package com.lon.lon_v3.mapper;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lon.lon_v3.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lon
 * @since 2023-03-09
 */
@Repository
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {



}

package com.lon.lon_v3.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.lon_v3.entity.Dept;
import com.lon.lon_v3.mapper.DeptMapper;
import com.lon.lon_v3.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.service.Impl
 * @className: DeptServiceImpl
 * @author: LONZT
 * @description: TODO
 * @date: 2023/6/14 15:49
 * @version: 1.0
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper,Dept> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;


    @Override
    public Map<String, String> getDeptMap() {
        QueryWrapper queryWrapper =new QueryWrapper<>();
        queryWrapper.lt("id",9999);
        queryWrapper.ge("id",999);
        List list = deptMapper.selectList(queryWrapper);
        Map<String, String> map = (Map<String, String>) list.stream().collect(Collectors.toMap(Dept::getId,Dept::getDeptName));
        return map;
    }
}

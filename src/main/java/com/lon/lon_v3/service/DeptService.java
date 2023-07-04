package com.lon.lon_v3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lon.lon_v3.comomon.RedisConstant;
import com.lon.lon_v3.entity.Dept;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.service
 * @className: DeptService
 * @author: LONZT
 * @description: TODO
 * @date: 2023/6/14 15:50
 * @version: 1.0
 */
public interface DeptService extends IService<Dept> {
    @Cacheable(cacheNames = RedisConstant.CacheNames.CACHE_15_DAY)
    Map<String,String> getDeptMap();
}

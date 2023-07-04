package com.lon.lon_v3.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lon.lon_v3.comomon.RedisConstant;
import com.lon.lon_v3.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.service
 * @className: UserService
 * @author: LONZT
 * @description: TODO
 * @date: 2023/5/9 14:48
 * @version: 1.0
 */
public interface UserService extends IService<User> {
    @Cacheable(cacheNames = RedisConstant.CacheNames.CACHE_5_MINUTE)
    Page<User> findUserByName(String name);

    @Cacheable(cacheNames = RedisConstant.CacheNames.CACHE_5_MINUTE)
    Page<User> findUsersByName(String name);

    Page<User> findUserByUno(Page page,@Param("uno") String uno);

    User findUserRankByName(@Param("uno") String uno);

    void dynamicsInsert(@Param("paramSQL") String sql);

    void dynamicsUpdate(@Param("paramSQL") String sql);

    void dynamicsDelete(@Param("paramSQL") String sql);
}

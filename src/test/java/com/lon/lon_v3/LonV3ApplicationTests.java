package com.lon.lon_v3;

import com.alibaba.excel.EasyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lon.lon_v3.entity.User;
import com.lon.lon_v3.entity.vo.UserExcel;
import com.lon.lon_v3.mapper.DeptMapper;
import com.lon.lon_v3.mapper.RankMapper;
import com.lon.lon_v3.mapper.UserMapper;
import com.lon.lon_v3.mapstruct.UserConvertUserExcel;
import com.lon.lon_v3.service.DeptService;

import com.lon.lon_v3.utils.CollectionsUtils;
import com.lon.lon_v3.utils.ExcelUtils;
import com.lon.lon_v3.utils.FileUtils;
import com.lon.lon_v3.entity.vo.FileVo;



import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;



@Slf4j
@SpringBootTest
class LonV3ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RankMapper rankMapper;

    @Autowired
    private DeptMapper deptMapper;
    @Test
    void contextLoads() {
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("uno","632101151328");
        int delete = userMapper.delete(queryWrapper);
        System.out.println(delete);

    }

    @Test
    void contextLoa() {

        System.out.println(userMapper.findUserRankByName("710120220009"));
//         System.out.println(rankMapper.findUsersRank(2,1002));
//        List<User> users = userMapper.findUserByName("赵%");
//        System.out.println(users);
    }


    @Test
    void contextLoad() {
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("uno","710120220009");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);

    }


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void redis() {

           redisTemplate.opsForValue().set("name", "赵腾龙",5, TimeUnit.MINUTES);

          System.out.println(redisTemplate.opsForValue().get("name"));

    }

    @Autowired
    private DeptService deptService;

    @Test
    void deptMapper() {

        System.out.println(deptService.getDeptMap());


    }

    @Test
    void out() {

        System.out.println(ResourceUtils.CLASSPATH_URL_PREFIX);

    }


    private final static String filePath= "E:\\file\\excel\\";
    @Test
    void file() {
        List<FileVo> fileNameLists = FileUtils.traverseFolder(filePath);
        System.out.println(fileNameLists);

    }



    @Test
    void excel() {
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.likeRight("uno","71012022");
        List<User> list = userMapper.selectList(queryWrapper);

        //        向Excel中写入数据 也可以通过 head(Class<?>) 指定数据模板
        Map<String,String> map = deptService.getDeptMap();
        System.out.println(map);
        System.out.println(map.get("1014"));

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            user.setDept(map.get(user.getDept()));
            list.set(i, user);
        }
        List<UserExcel> userExcels = UserConvertUserExcel.INSTANCE.toConvertExcel(list);
        System.out.println(userExcels);
        String fileName = filePath + "userExcel1.xlsx";
        EasyExcel.write(fileName, UserExcel.class)
                .sheet("用户信息")
                .doWrite(userExcels);

        //excel转换为pdf



    }

    @Test
    void readExcel() {

        /**
         * 缓存的数据
         */
        List<UserExcel> cachedDataList = new ArrayList<>();
        String filename = "E:\\file\\excel\\userExcel1.xlsx";
        // 创建ExcelReaderBuilder对象
        ExcelReaderBuilder readerBuilder = EasyExcel.read();
        // 获取文件对象
        readerBuilder.file(filename);
        // 指定映射的数据模板
         readerBuilder.head(UserExcel.class);
        // 指定sheet
        readerBuilder.sheet(0);
        // 自动关闭输入流
        readerBuilder.autoCloseStream(true);
        // 设置Excel文件格式
        readerBuilder.excelType(ExcelTypeEnum.XLSX);
        // 注册监听器进行数据的解析
        readerBuilder.registerReadListener(new AnalysisEventListener() {
            // 每解析一行数据,该方法会被调用一次
            @Override
            public void invoke(Object demoData, AnalysisContext analysisContext) {
                // 如果没有指定数据模板, 解析的数据会封装成 LinkedHashMap返回
                // demoData instanceof LinkedHashMap 返回 true
//                System.out.println("解析数据为:" + demoData.toString());

                cachedDataList.add((UserExcel) demoData);
                System.out.println(cachedDataList.size());
            }

            // 全部解析完成被调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                Map<String,String> map = deptService.getDeptMap();

                System.out.println(cachedDataList);
                for (int i = 0; i < cachedDataList.size(); i++) {
                    UserExcel user = cachedDataList.get(i);
                    user.setDept(CollectionsUtils.getKeysByStream(map, user.getDept()).stream().toArray()[0].toString());
                    cachedDataList.set(i, user);
                }
                System.out.println(cachedDataList);
                System.out.println("解析完成...");
                // 可以将解析的数据保存到数据库
            }
        });
        readerBuilder.doReadAll();




    }



}

package com.lon.lon_v3.test;

import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.test
 * @className: SearcherTest
 * @author: LONZT
 * @description: TODO
 * @date: 2023/7/10 11:40
 * @version: 1.0
 */
public class SearcherTest {

    public static void main(String[] args) {
        // 1、创建 searcher 对象
        String dbPath = "D:\\Desktop\\6y\\ip2region.xdb";
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            System.out.printf("failed to create searcher with `%s`: %s\n", dbPath, e);
            return;
        }
        String ip = "1.14.93.249";
        // 2、查询
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
        }

        // 3、备注：并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
    }
}

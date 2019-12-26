package com.qjq.redpackage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class RedPackageFileUitils {

    /**
     * 获取文件中红包金额对象
     * @param fileName
     * @return
     */
    List<RedPackage> getRedPackage(String fileName){
        List<RedPackage> data =  Lists.newArrayList();
        BigFileReader.Builder builder = new BigFileReader.Builder("", line -> {
            String[] str = line.split("\\s+");
            data.add(new RedPackage(Long.valueOf(str[0]),Long.valueOf(str[1])));
        });
        builder.withTreahdSize(10)
                .withCharset("UTF-8")
                .withBufferSize(1024*1024);
        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();
        return data;
    }




    /**
     * 获取账户信息
     * @param fileName
     * @return
     */
    Map<Long,AtomicLong> getAccount(String fileName){
        Map<Long,AtomicLong> data = Maps.newConcurrentMap();
        BigFileReader.Builder builder = new BigFileReader.Builder(fileName, line -> {
            String[] str = line.split("\\s+");
            data.put(Long.valueOf(str[0]),new AtomicLong(Long.valueOf(str[1])));
        });
        builder.withTreahdSize(10)
                .withCharset("UTF-8")
                .withBufferSize(1024*1024);
        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();
        return data;
    }

}

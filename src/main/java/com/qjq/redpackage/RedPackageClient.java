package com.qjq.redpackage;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class RedPackageClient {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        RedPackageFileUitils fileUtils = new RedPackageFileUitils();

        //存储所有账户信息
        Map<Long,AtomicLong> accountMap = new ConcurrentHashMap<>(2000*10000);
        for(int i=0;i<100;i++){
            accountMap.putAll(fileUtils.getAccount("d:/account"+i+".txt"));
        }

        File file = new File("d:/red/");
        // get the folder list
        File[] array = file.listFiles();
        List<String> fileNames = Lists.newArrayList();
        for (int i = 0; i < array.length; i++) {
            fileNames.add(array[i].getName());
        }
        ExecutorService pool = Executors.newFixedThreadPool(100);
        fileNames.parallelStream().forEach(d->{
            List<RedPackage> packageList = fileUtils.getRedPackage(d);
            RedPackageManager redPackageManager =new RedPackageManager();
            Map<Long,Long> redData = redPackageManager.dealRepeatPackage(packageList);
            RedPackageJob  job = new RedPackageJob();
            job.setRedPackageMap(redData);
            job.setAccountMap(accountMap);
            CompletableFuture<Map<Long, AtomicLong>> future =CompletableFuture.supplyAsync(job,pool);
        });




        TimeUnit.SECONDS.sleep(1L);
//        System.out.println(future.toCompletableFuture());
    }
}

package com.qjq.redpackage;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class RedPackageClient {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        RedPackageFileUitils fileUtils = new RedPackageFileUitils();

        //存储所有账户信息
        Map<Integer,List<Account>> accountMap = new ConcurrentHashMap<>();
        for(int i=0;i<100;i++){
           List<Account>  accounts = fileUtils.getAccount("d:/account"+i+".txt");
           accountMap.put(i,accounts);
        }

        File file = new File("d:/red/");
        // get the folder list
        File[] array = file.listFiles();
        List<String> fileNames = Lists.newArrayList();
        for (int i = 0; i < array.length; i++) {
            fileNames.add(array[i].getName());
        }
//        fileNames.parallelStream().forEach();
        List<RedPackage> packageList = fileUtils.getRedPackage("d:/redPackage.txt");

        RedPackageManager redPackageManager =new RedPackageManager();
        Map<Long,Long> redData = redPackageManager.dealRepeatPackage(packageList);


        ExecutorService pool = Executors.newFixedThreadPool(20);

        RedPackageJob  job = new RedPackageJob();
//        job.setAccountMap(accountData);
        job.setRedPackageMap(redData);
        CompletableFuture<Map<Long,Long>> future =CompletableFuture.supplyAsync(job,pool);

//        pool.submit(job);
//        TimeUnit.SECONDS.sleep(1L);
        System.out.println(future.toCompletableFuture());
    }
}

package com.qjq.redpackage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class RedPackageJob implements Supplier<Map<Long,AtomicLong>> {

    private String  outFileName;
    //这个设进去并发的hash可以不用重新new对象
    private Map<Long, AtomicLong>  accountMap;

    private Map<Long, Long> redPackageMap;


    public String getOutFileName() {
        return outFileName;
    }

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }

    public Map<Long, AtomicLong> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<Long, AtomicLong> accountMap) {
        this.accountMap = accountMap;
    }

    public Map<Long, Long> getRedPackageMap() {
        return redPackageMap;
    }

    public void setRedPackageMap(Map<Long, Long> redPackageMap) {
        this.redPackageMap = redPackageMap;
    }

    @Override
    public Map<Long, AtomicLong> get() {
        for(Long id:accountMap.keySet()){
            AtomicLong data =  accountMap.get(id);
            accountMap.put(id,new AtomicLong(data.addAndGet(redPackageMap.get(id))));
        }
        return accountMap;
    }
}

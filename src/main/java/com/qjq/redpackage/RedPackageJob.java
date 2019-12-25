package com.qjq.redpackage;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RedPackageJob implements Supplier<Map<Long,Long>> {

    private String  outFileName;
    //这个设进去并发的hash可以不用重新new对象
    private Map<Long, Long>  accountMap;

    private Map<Long, Long> redPackageMap;


    public String getOutFileName() {
        return outFileName;
    }

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }

    public Map<Long, Long> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<Long, Long> accountMap) {
        this.accountMap = accountMap;
    }

    public Map<Long, Long> getRedPackageMap() {
        return redPackageMap;
    }

    public void setRedPackageMap(Map<Long, Long> redPackageMap) {
        this.redPackageMap = redPackageMap;
    }

    @Override
    public Map<Long, Long> get() {
        for(Long id:accountMap.keySet()){
            Long data =  accountMap.get(id);
            accountMap.put(id,redPackageMap.get(id)+data);
        }
        return accountMap;
    }
}

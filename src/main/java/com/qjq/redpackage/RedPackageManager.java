package com.qjq.redpackage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedPackageManager {

    /**
     * 把每个红包中的金额先合并
     * @return
     */
    public Map<Long,Long> dealRepeatPackage(List<RedPackage> redList){
       Map<Long, Long> redSumList = redList.parallelStream().collect(Collectors.groupingBy(RedPackage::getId,Collectors.summingLong(RedPackage::getMoney)));
       return redSumList;
    }



}

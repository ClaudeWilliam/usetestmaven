package com.qjq.guava.model;

import com.qjq.guava.annotation.CloudFiled;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class CloudMan {
    @CloudFiled(name = "姓名")
    String name;
    @CloudFiled(name = "地址")
    String address;
    @CloudFiled(name = "女")
    String sex;

}

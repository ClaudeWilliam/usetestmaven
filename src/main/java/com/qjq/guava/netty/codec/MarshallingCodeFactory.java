package com.qjq.guava.netty.codec;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public class MarshallingCodeFactory {

    /**
     * 创建marshalling解码(反序列化)
     *
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        //创建marshalling的工厂对象，指定名字
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();//创建配置对象
        configuration.setVersion(5);//设置版本
        //根据工厂和配置创建一个解码的provider
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(factory, configuration);
        //创建解码对象，使用provider对象和最大对象大小。一般解码是指定大小，但是一般编码是不指定大小，其实解码也不是一定要指定
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 10 * 1024 * 1024);
        return decoder;
    }

    /**
     * 创建marshalling编码(序列化)
     *
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        //创建marshalling的工厂对象，指定名字
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();//创建配置对象
        configuration.setVersion(5);//设置版本
        //根据工厂和配置创建一个编码的provider
        MarshallerProvider provider = new DefaultMarshallerProvider(factory, configuration);
        //创建编码对象，使用provider对象和最大对象大小
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}
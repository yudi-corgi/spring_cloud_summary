package com.cloudalibaba.nacos.api.config;

import feign.FeignException;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author YUDI-Corgi
 * @description Feign 自定义编解码器实现
 */
public class FeignCodec implements Decoder, Encoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        // do something...
        return null;
    }

    @Override
    public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {
        // do something...
    }

}

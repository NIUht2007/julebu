package com.jczy.cyclone.club.network;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 商城 OpenApi 服务类型注解
 * 通过拦截器读取，自动将 service 字段注入请求体
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenApiService {
    String service();
}

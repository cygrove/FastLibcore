package com.xiongms.libcore.config;

/**
 * 
 * @author cygrove
 * @time 2018-11-16 14:19
 */
public interface NetConfig {

    int NET_TIME_OUT_CONNECT = 60;
    int NET_TIME_OUT_READ = 60;
    int NET_TIME_OUT_WRITE = 60;

    int NET_MAX_RETRY_TIMES = 0;//暂不使用重试
}

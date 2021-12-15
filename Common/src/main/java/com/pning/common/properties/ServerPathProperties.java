package com.pning.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author Pning
 * @Date 2021/12/14 17:00
 * DFS相关服务器地址和端口
 **/

@PropertySource(value = { "classpath:fastdfs.yml" })
public class ServerPathProperties {
    public String FastDFSServerPath;
}

package com.swpym.blog.config;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.swpym.blog.config.properties.AiOssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-05-09 15:01
 */
@Configuration
public class AiOssConfig {

    @Autowired
    private AiOssProperties aiOssProperties;

    @Bean
    public OSSClient ossClient(){
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(aiOssProperties.getConnectionTimeout());
        conf.setMaxErrorRetry(aiOssProperties.getMaxErrorRetry());
        return new OSSClient(aiOssProperties.getEndpoint(),aiOssProperties.getAccessKeyId(),
                aiOssProperties.getAccessKeySecret());
    }

}

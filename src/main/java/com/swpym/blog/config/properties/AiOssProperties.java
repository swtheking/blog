package com.swpym.blog.config.properties;

import com.aliyun.oss.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-05-09 14:49
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AiOssProperties {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String url;

    private String path;

    private Integer maxErrorRetry;

    private Integer connectionTimeout;

    public OSS getOssClient() {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setConnectionTimeout(connectionTimeout);
        conf.setMaxErrorRetry(maxErrorRetry);
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
    }

}

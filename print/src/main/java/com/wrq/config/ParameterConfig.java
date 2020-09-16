package com.wrq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by wangqian on 2019/3/30.
 */
@Component
@ConfigurationProperties(prefix = "config")
@Data
public class ParameterConfig {

    private String imageHost;

    private String alipayCallbackUrl;

    private String shopNotFound;

}

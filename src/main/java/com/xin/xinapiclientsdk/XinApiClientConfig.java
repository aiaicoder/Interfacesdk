package com.xin.xinapiclientsdk;

import com.xin.xinapiclientsdk.client.XinApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xin
 */
@Configuration
@ConfigurationProperties(prefix = "xinapi.client")
@Data
@ComponentScan
public class XinApiClientConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public XinApiClient xinApiClient() {
        return new XinApiClient(accessKey, secretKey);
    }
}

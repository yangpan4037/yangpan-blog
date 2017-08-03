package site.yangpan.core.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yangpn on 2017-08-03 11:39
 */
@Configuration
@EnableConfigurationProperties(ServiceProperties.class)
public class ServiceConfiguration {
    @Bean
    public Service service(ServiceProperties properties) {
        return new Service(properties.getMessage());
    }
}
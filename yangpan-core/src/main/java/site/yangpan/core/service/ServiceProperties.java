package site.yangpan.core.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by yangpn on 2017-08-03 11:38
 */
@ConfigurationProperties("service")
public class ServiceProperties {

    /**
     * A message for the service.
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

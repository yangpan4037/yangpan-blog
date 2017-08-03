package site.yangpan.core.service;

import org.springframework.stereotype.Component;

/**
 * Created by yangpn on 2017-08-03 11:38
 */
@Component
public class Service {
    private final String message;

    public Service(String message) {
        this.message = message;
    }

    public String message() {
        return this.message;
    }
}

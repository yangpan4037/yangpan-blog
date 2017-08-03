package site.yangpan.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import site.yangpan.core.service.Service;
import site.yangpan.core.service.ServiceConfiguration;

/**
 * Created by yangpn on 2017-08-03 11:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest("service.message=Hello")
public class ServiceTest {

    @Autowired
    private Service service;

    @Test
    public void contextLoads() {
        System.out.println((service.message()));
    }

    @SpringBootApplication
    @Import(ServiceConfiguration.class)
    static class TestConfiguration {
    }

}

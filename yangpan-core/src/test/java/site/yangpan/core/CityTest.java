package site.yangpan.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import site.yangpan.core.dao.CityService;

/**
 * Created by yangpn on 2017-08-01 15:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CityTest {

    @Autowired
    private CityService cityService;

    @Test
    public void save(){
        String name = this.cityService.getCity("Bath", "UK").getName();
        System.out.println(name);
    }
}

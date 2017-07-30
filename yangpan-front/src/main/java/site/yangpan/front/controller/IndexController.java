package site.yangpan.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.yangpan.core.dao.CityService;

import javax.transaction.Transactional;

/**
 * Created by yangpn on 2017-07-30 10:44
 */
@Controller
public class IndexController {


    @Autowired
    private CityService cityService;

    @GetMapping("/")
    @ResponseBody
    @Transactional
    private String helloWorld() {
        return this.cityService.getCity("Bath", "UK").getName();
    }
}

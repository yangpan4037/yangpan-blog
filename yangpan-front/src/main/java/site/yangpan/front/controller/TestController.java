package site.yangpan.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.yangpan.core.service.CityService;
import site.yangpan.core.domain.City;

/**
 * Created by yangpn on 2017-08-02 09:35
 */
@Controller
@RequestMapping("yangpan")
public class TestController {
    @Autowired
    private CityService cityService;

    @GetMapping
    private String helloWorld(ModelMap model) {
        City city = cityService.getCity("Brisbane", "Australia");
        model.addAttribute("city",city);
        System.out.println(11);
        return "index";
    }
}

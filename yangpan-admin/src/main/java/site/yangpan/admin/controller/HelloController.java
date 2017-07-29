package site.yangpan.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yangpn on 2017-07-28 17:32
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping
    ModelAndView home() {
        return new ModelAndView("hello");
    }
}

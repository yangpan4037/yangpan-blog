package site.yangpan.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import site.yangpan.core.dao.CityService;

@Controller
@SpringBootApplication //@SpringBootApplication注解相当于使用@Configuration，@EnableAutoConfiguration和@ComponentScan和他们的默认属性
public class Application{

    @Autowired
    private CityService cityService;

    @GetMapping("/")
    @ResponseBody
    @Transactional(readOnly = true)
    public String helloWorld() {
        return this.cityService.getCity("Bath", "UK").getName();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
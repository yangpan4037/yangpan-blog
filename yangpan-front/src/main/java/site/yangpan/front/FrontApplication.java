package site.yangpan.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import site.yangpan.core.CoreApplication;
import site.yangpan.core.dao.CityService;

@Controller
@ComponentScan( basePackages = {"site.yangpan"})
@SpringBootApplication //@SpringBootApplication注解相当于使用@Configuration，@EnableAutoConfiguration和@ComponentScan和他们的默认属性
public class FrontApplication {

    @Autowired
    private CityService cityService;

    @GetMapping("/yangpan")
    private String helloWorld() {
        return "index";
    }

    public static void main(String[] args) {
//        SpringApplication.run(FrontApplication.class,args);
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.CONSOLE)
                .sources(CoreApplication.class, FrontApplication.class)
                .run(args);
    }


}
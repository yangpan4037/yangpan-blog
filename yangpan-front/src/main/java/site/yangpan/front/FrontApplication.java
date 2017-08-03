package site.yangpan.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import site.yangpan.core.service.CityService;

/**
 * Created by yangpn on 2017-08-03 11:44
 */
//@ComponentScan(basePackages = {"site.yangpan"})
//@SpringBootApplication

@SpringBootApplication
@ComponentScan({"site.yangpan.core","site.yangpan.front"})
@EntityScan("site.yangpan.core.domain")
@EnableJpaRepositories("site.yangpan.core.repository")
@Controller
public class FrontApplication {

    @Autowired
    private CityService cityService;

    @GetMapping("/")
    public String home() {
        System.out.println(11);
        return "index";
    }

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "fase");
        SpringApplication.run(FrontApplication.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(FrontApplication.class);
//    }

}
package site.yangpan.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import site.yangpan.core.CoreApplication;
import site.yangpan.core.dao.CityService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.Transactional;

@Controller
@SpringBootApplication() //@SpringBootApplication注解相当于使用@Configuration，@EnableAutoConfiguration和@ComponentScan和他们的默认属性
public class FrontApplication {

    @Autowired
    private CityService cityService;

    @GetMapping("/yangpan")
    @Transactional
    private String helloWorld(ModelMap model) {
        String name = this.cityService.getCity("Bath", "UK").getName();
        model.addAttribute("name",name);
        return "index";
    }

    public static void main(String[] args) throws Exception {
//        SpringApplication.run(Application.class, args);
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.CONSOLE)
                .sources(CoreApplication.class, FrontApplication.class)
                .run(args);
        System.out.println("99999999999999999999999999999999999");
    }


    @Bean
    protected ServletContextListener listener() {
        return new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                System.out.println("1111111111111111111111111111111111111111");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                System.out.println("2222222222222222222222222222222222222222222222");
            }

        };
    }
}
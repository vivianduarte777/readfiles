package apirestfiles.readfiles.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("/").setViewName("index");
       registry.addViewController("/").setViewName("result");
    }

}

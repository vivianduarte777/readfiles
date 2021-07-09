package apirestfiles.readfiles.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class TestRestTemplateConfiguration {
    @Bean
    public TestRestTemplate TestRestTemplateConfiguration(ObjectProvider<RestTemplateBuilder> builderObjectProvider,//
     Environment environment){
        RestTemplateBuilder builder = builderObjectProvider.getIfAvailable();

        TestRestTemplate template = builder == null ?new TestRestTemplate():new TestRestTemplate(builder);
        return template;
    }
}

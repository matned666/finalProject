package eu.mnrdesign.matned.final_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class WebsiteUrlConfig {

    @Value("${www.domain.url}")
    private String webUrl;

    @Bean
    public String webUrl() {
        return this.webUrl;
    }

}

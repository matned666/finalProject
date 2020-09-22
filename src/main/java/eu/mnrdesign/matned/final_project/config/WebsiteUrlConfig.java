package eu.mnrdesign.matned.final_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class WebsiteUrlConfig {

    @Value("${spring.mail.username}")
    private String incomingMailBox;

    @Value("${secret.key.for.session.token}")
    private String secretKey;

    @Value("${www.domain.url}")
    private String webUrl;

    @Value("${token.expiry.minutes}")
    private Integer tokenExpMinutes;

    @Value("${token.length.signs}")
    private Integer tokenLength;

    @Value("${default.admin.username}")
    private String defaultAdminLogin;

    @Value("${default.admin.password}")
    private String defaultAdminPassword;







    @Bean
    public Integer tokenLength() {
        return this.tokenLength;
    }

    @Bean
    public String secretKey() {
        return this.secretKey;
    }

    @Bean
    public Integer tokenExpMinutes() {
        return this.tokenExpMinutes;
    }

    @Bean
    public String webUrl() {
        return this.webUrl;
    }

    @Bean
    public String incomingMailBox() { return this.incomingMailBox; }

    @Bean
    public String defaultAdminLogin() { return this.defaultAdminLogin; }

    @Bean
    public String defaultAdminPassword() { return this.defaultAdminPassword; }


}

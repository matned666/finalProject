package eu.mnrdesign.matned.final_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
public class RememberBeConfig {



}





//<bean id="rememberMeFilter" class=
//        "org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter">
//<property name="rememberMeServices" ref="rememberMeServices"/>
//<property name="authenticationManager" ref="theAuthenticationManager" />
//</bean>
//
//<bean id="rememberMeServices" class=
//        "org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices">
//<property name="userDetailsService" ref="myUserDetailsService"/>
//<property name="key" value="springRocks"/>
//</bean>
//
//<bean id="rememberMeAuthenticationProvider" class=
//        "org.springframework.security.authentication.rememberme.RememberMeAuthenticationProvider">
//<property name="key" value="springRocks"/>
//</bean>
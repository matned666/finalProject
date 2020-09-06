package eu.mnrdesign.matned.final_project.config;

import eu.mnrdesign.matned.final_project.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ADMIN_ADMIN_PL = "admin@admin.pl";
    public static final String USER_USER_PL = "user@user.pl";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register", "/register/*").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/tasks", "/tasks/*", "/tasks/**","/task", "/task/*", "/task/**")
                .hasAnyRole(UserRole.Role.ADMIN.name(), UserRole.Role.USER.name())
                .antMatchers("/about").permitAll()
                .antMatchers("/account", "/account/*", "/account/**")
                .hasAnyRole(UserRole.Role.ADMIN.name(), UserRole.Role.USER.name())
                .antMatchers("/users-list", "/users-list/*", "/users-list/**")
                .hasRole(UserRole.Role.ADMIN.name())
                .antMatchers("/nav").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
//                .cors().disable()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login-process") //here goes login data
                .failureUrl("/login?error=1")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(ADMIN_ADMIN_PL)
                .password(passwordEncoder.encode("admin"))
                .roles(UserRole.Role.ADMIN.name())
                .and()
                .withUser(USER_USER_PL)
                .password(passwordEncoder.encode("user"))
                .roles(UserRole.Role.ADMIN.name());

        auth.jdbcAuthentication()
                .usersByUsernameQuery("select u.LOGIN, u.PASSWORD, 1 from USER u where u.LOGIN = ?")
                .authoritiesByUsernameQuery(
                        "select u.LOGIN, r.ROLE_NAME from USER u " +
                                "join USER_ROLES ur on u.ID = ur.USER_ID " +
                                "join USER_ROLE r on ur.ROLES_ID = r.ID " +
                                "where u.LOGIN = ?")
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);


    }

    //cors configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}

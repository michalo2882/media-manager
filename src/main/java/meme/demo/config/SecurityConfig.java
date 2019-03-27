package meme.demo.config;

import meme.demo.security.DbUserDetailsService;
import meme.demo.security.RestAuthenticationEntryPoint;
import meme.demo.security.RestAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    private SimpleUrlAuthenticationFailureHandler restAuthenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Autowired
    private DbUserDetailsService dbUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(dbUserDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .formLogin()
                .successHandler(restAuthenticationSuccessHandler)
                .failureHandler(restAuthenticationFailureHandler)
                .and()
                .logout();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

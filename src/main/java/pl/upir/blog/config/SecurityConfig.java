package pl.upir.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;
import pl.upir.blog.service.security.BlgUserSecurityServiceImpl;
import pl.upir.blog.web.util.MD5Encoder;

import javax.sql.DataSource;

/**
 * Created by Vitalii on 07.07.2015.
 */
//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired(required = false)
    SecurityUserDetailService securityUserDetailService;
  /*  @Autowired
    RoleVoter roleVoter;*/

    @Override
    protected void configure(AuthenticationManagerBuilder registry) throws Exception {
        registry.userDetailsService(securityUserDetailService).passwordEncoder(new MD5Encoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("public/**"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/sign", "sign/signout", "/").permitAll() // #4
                .regexMatchers("A/users.*Z").hasRole("ADMIN") // #6
                .antMatchers("/j_spring_security_logout").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated() // 7
                .and()
                .formLogin()  // #8
                .loginPage("/sign") // #9
                .loginProcessingUrl("/sign/signin")
                .failureUrl("/sign?error=login").defaultSuccessUrl("/").usernameParameter("j_username").passwordParameter("j_password")
                .permitAll()
                .and()
                .logout().logoutUrl("j_spring_security_logout").logoutSuccessUrl("/")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .sessionManagement().invalidSessionUrl("/"); // #5
    }


}

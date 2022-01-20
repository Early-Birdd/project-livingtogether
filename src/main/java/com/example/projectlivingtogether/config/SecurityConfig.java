package com.example.projectlivingtogether.config;

import com.example.projectlivingtogether.service.AdminService;
import com.example.projectlivingtogether.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Autowired
    AdminService adminService;

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/", "/members/**", "/admins/**", "/all/**", "/item/**", "/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/all/login")
                        .defaultSuccessUrl("/")
                        .usernameParameter("email")
                        .failureUrl("/all/login/error")
                        .and()
                        .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/all/logout"))
                        .logoutSuccessUrl("/")
                .and()
                    .exceptionHandling()
                        .authenticationEntryPoint(new MemberAuthentication());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());

        auth.userDetailsService(adminService)
                .passwordEncoder(passwordEncoder());
    }
}

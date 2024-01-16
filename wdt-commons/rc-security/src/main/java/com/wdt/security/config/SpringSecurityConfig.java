package com.wdt.security.config;

import com.wdt.security.filter.LoginFilter;
import com.wdt.security.handler.*;
import com.wdt.security.service.impl.DefaultUserDetailsServiceImpl;
import com.wdt.security.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Description:
 * Author: admin
 * Date: 2024/1/10
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Resource
    private DefaultAccessDeniedHandler defaultAccessDeniedHandler ;
    @Resource
    private RedisUtil redisUtil ;
    @Resource
    private DefaultLoginOutSuccessHandler defaultLoginOutSuccessHandler ;
    @Resource
    private DefaultUserDetailsServiceImpl defaultUserDetailsService ;
    @Resource
    private AuthenticationConfiguration authenticationConfiguration ;

    @Bean
    public AuthenticationManager authenticationManager()  {
        try {
            return authenticationConfiguration.getAuthenticationManager();
        }catch (Exception e){
            return null ;
        }
    }

    @Bean
    public LoginFilter getLoginFilter() {
        return new LoginFilter(authenticationManager(),redisUtil);
    }


    /**
     * 取消ROLE_前缀
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    /**
     * 设置密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DefaultPassWordEncoder();
    }

    /**
     * 设置默认认证提供
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(defaultUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * 安全配置
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api-security/login").permitAll()
                        .anyRequest().authenticated()
                );
        http.formLogin(form->form.loginProcessingUrl("/api-security/login"));
        http.addFilterAt(getLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(ex->ex.accessDeniedHandler(defaultAccessDeniedHandler));
        http.logout(logout->logout.logoutSuccessHandler(defaultLoginOutSuccessHandler));
        http.logout(from->from.logoutUrl("/api-security/logout"));
        return http.build();
    }

}
package com.wdt.security.config;

import com.wdt.security.filter.LoginAuthFilter;
import com.wdt.security.filter.LoginFilter;
import com.wdt.security.handler.*;
import com.wdt.security.service.impl.DefaultUserDetailsServiceImpl;
import com.wdt.security.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    private DefaultUnOauthEntryPoint defaultUnOauthEntryPoint;
    @Resource
    private DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;
    @Resource
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;
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
    public LoginAuthFilter getLoginAuthFilter() {
        return new LoginAuthFilter(authenticationManager(), redisUtil);
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
//    /**
//     * 设置中文配置
//     */
//    @Bean
//    public ReloadableResourceBundleMessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:org/springframework/security/messages_zh_CN");
//        return messageSource;
//    }

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
    /**
     * 安全配置
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        //不通过Session获取SecurityContext
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // /security/login 无需认证 ，其余都需要认证
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api-security/login").permitAll()
                        .anyRequest().authenticated()
                );
        http.formLogin(form->form.loginProcessingUrl("/api-security/login"));
        //认证过滤器
        http.addFilterAt(getLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        //把token校验过滤器添加到过滤器链中
        //鉴权过滤器
        http.addFilterBefore(getLoginAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        // 全局异常处理器  认证异常和鉴权异常
        http.exceptionHandling(ex->ex.authenticationEntryPoint(defaultUnOauthEntryPoint).accessDeniedHandler(defaultAccessDeniedHandler));
        // 鉴权成功 和 鉴权 失败处理器
        http.formLogin(form->form.successHandler(defaultAuthenticationSuccessHandler).failureHandler(defaultAuthenticationFailureHandler));
        //登出成功处理器
        http.logout(logout->logout.logoutSuccessHandler(defaultLoginOutSuccessHandler));
        http.logout(from->from.logoutUrl("/api-security/logout"));
        return http.build();
    }

}
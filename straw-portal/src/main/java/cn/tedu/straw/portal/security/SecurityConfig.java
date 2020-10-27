package cn.tedu.straw.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableRedisHttpSession
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable();
//    }

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    FindByIndexNameSessionRepository sessionRepository;

    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Override
    protected void configure(HttpSecurity http)throws  Exception{
        // 登录页面的URL
        String loginPageUrl = "/login.html";
        // 处理登录请求的URL
        String loginProcessingUrl = "/login";
        // 登录失败后的URL
        String loginFailureUrl = "/login.html?error";
        // 登录成功后的URL
        String loginSuccessUrl = "/index.html";
        // 退出登录的URL
        String logoutUrl = "/logout";
        // 退出登录成功后的URL
        String logoutSuccessUrl = "/login.html?logout";

        //csrf().disable()关闭跨域攻击
        //authorizeRequests() 对请求进行授权
        //antMatchers() 配置访问白名单
        //permitAll()对白名单中的路劲进行授权
        //anyRequest()其他的请求路径
        //authenticated() 仅经过授权的允许访问,也可以理解为 位被授权的不允许访问
       //and().formLogin()未bewilder授权的通过登录表单进行验证
        String[]antMatchers={
                "/login.html",
                "/register.html",
                "/api/v1/user/student/register",
                "/bower_components/**",
                "/css/**",
                "/img/**",
                "/js/**"

        };
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(antMatchers).permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage(loginPageUrl)
                .loginProcessingUrl(loginProcessingUrl)
                .failureUrl(loginFailureUrl)
                .defaultSuccessUrl(loginSuccessUrl)
                .and().logout()
                .logoutUrl(logoutUrl)
                .logoutSuccessUrl(logoutSuccessUrl);

        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("java")
//                .password("{bcrypt}$2a$10$tsM03ULkiifEpSCWtQ5Mq.yrLZIPKVr5vHwU1FGjtT9B1vPlswa.C")
//                .authorities("/test");
//    }




}

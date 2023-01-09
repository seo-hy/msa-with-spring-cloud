package com.example.userservice.security;

import com.example.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private UserService userService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private Environment env;

  public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.env = env;
    this.userService = userService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }


  // 권한
  @Override
  protected void configure(HttpSecurity http) throws Exception { // 권한
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/**")
        .hasIpAddress("172.30.1.68") // 본인 IP
        .and()
        .addFilter(getAuthenticationFilter()); // filter를 통과해야 접근 가능
    http.headers().frameOptions().disable(); // h2-console 접속 위함

  }
  private AuthenticationFilter getAuthenticationFilter() throws Exception {

    AuthenticationFilter authenticationFilter =
        new AuthenticationFilter(authenticationManager(), userService, env);

    return authenticationFilter;
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 인증
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
                      // userDetailsService를 상속받은 sevice여야함
    // userDetailsService : username과 password를 받아 로그인 처리
    // 내부로직 : select pwd from users where email=?
    //          db_pwd(encrypted) == input_pwd(encrypted)
  }

}

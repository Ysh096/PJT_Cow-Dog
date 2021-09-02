package com.xy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xy.auth.JwtFilter;
import com.xy.auth.MemberDetailsService;
import com.xy.service.MemberServiceImpl;

@Configuration
@EnableWebSecurity // 기본적인 웹 시큐리티를 쓴다고
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MemberServiceImpl memberService;
	@Autowired
	private MemberDetailsService memberDetailService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private JwtFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberDetailService);
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		System.out.println("sdfasf");
		    http.csrf().disable().authorizeRequests().antMatchers("/cowdog/mem/login","/cowdog/mem/confirmId",
		    		"/cowdog/mem/confirmNickname","/cowdog/mem/register","/cowdog/mem/profileImgaeUpload","/ cowdog/mem/getImageList"
		    		,"/cowdog/EmailAuthentication/searchPassword", "cowdog/mem/logout","/","cowdog/","/chat/info","/chat/**")//회원로그인 , 회원가입 , 아이디 검사, 닉네임 검사, 소켓연결은 요청 받아들인다.
		    .permitAll().anyRequest().permitAll()//나머지 요청은 토큰을 확인
		    .and().exceptionHandling().and().sessionManagement()
		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		    http.cors().and();
    }


}
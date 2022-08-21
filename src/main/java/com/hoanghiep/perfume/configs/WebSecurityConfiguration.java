package com.hoanghiep.perfume.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import com.hoanghiep.perfume.security.JwtAuthenticationEntryPoint;
import com.hoanghiep.perfume.security.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	private final JwtFilter jwtFilter;
	private final JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and()
		.csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(unauthorizedHandler)
		.and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/hust/auth/**",
                "/api/hust/auth/login",
                "/api/hust/auth/registration/**",
                "/api/hust/perfumes/**",
                "/api/hust/users/cart",
                "/api/hust/users/order/**",
                "/api/hust/users/review",
                "/websocket", "/websocket/**",
                "/img/**",
                "/static/**").permitAll()
        .antMatchers("/auth/**","/swagger-ui.html", "/v3/api-docs/**","/actuator/**","/**/*swagger*/**").permitAll()
        //.antMatchers("/api/hust/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated();
        //.and().httpBasic()
        
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
}

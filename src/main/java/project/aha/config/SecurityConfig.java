package project.aha.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;
import project.aha.auth.jwt.JwtAccessDeniedHandler;
import project.aha.auth.jwt.JwtAuthenticationEntryPoint;
import project.aha.auth.jwt.JwtSecurityConfig;
import project.aha.auth.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/images/**", "/swagger-ui/**", "/v3/api-docs/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(request -> {
			CorsConfiguration cors = new CorsConfiguration();
			cors.setAllowedOrigins(List.of("*"));
			cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
			cors.setAllowedHeaders(List.of("*"));
			return cors;
		});
		// CSRF 설정 Disable
		http.csrf().disable()

			// exception handling 할 때 우리가 만든 클래스를 추가
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			// h2-console 을 위한 설정을 추가
			.and()
			.headers()
			.frameOptions()
			.sameOrigin()

			// 시큐리티는 기본적으로 세션을 사용
			// 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			// 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
			.antMatchers("/auth/**", "/ws/**").permitAll()
			.antMatchers("/image/**").permitAll()
			.antMatchers("/board/**").permitAll()
			.antMatchers(HttpMethod.GET, "/post/**", "/user/**").permitAll()
			//    .antMatchers("/post/**").permitAll()
			.anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요

			// JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
			.and()
			.apply(new JwtSecurityConfig(tokenProvider));
	}
}

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
		web.ignoring().antMatchers("/resources/**", "/images/**");
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
		// CSRF ?????? Disable
		http.csrf().disable()

			// exception handling ??? ??? ????????? ?????? ???????????? ??????
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			// h2-console ??? ?????? ????????? ??????
			.and()
			.headers()
			.frameOptions()
			.sameOrigin()

			// ??????????????? ??????????????? ????????? ??????
			// ???????????? ????????? ???????????? ?????? ????????? ?????? ????????? Stateless ??? ??????
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			// ?????????, ???????????? API ??? ????????? ?????? ???????????? ????????? ???????????? ????????? permitAll ??????
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
			.antMatchers("/auth/**", "/ws/**").permitAll()
			.antMatchers("/image/**").permitAll()
			.antMatchers("/board/**").permitAll()
			.antMatchers(HttpMethod.GET, "/post/**", "/user/**").permitAll()
			//    .antMatchers("/post/**").permitAll()
			.anyRequest().authenticated()   // ????????? API ??? ?????? ?????? ??????

			// JwtFilter ??? addFilterBefore ??? ???????????? JwtSecurityConfig ???????????? ??????
			.and()
			.apply(new JwtSecurityConfig(tokenProvider));
	}
}

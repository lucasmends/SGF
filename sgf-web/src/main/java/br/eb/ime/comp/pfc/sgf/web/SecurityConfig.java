package br.eb.ime.comp.pfc.sgf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * @Autowired private AuthenticationProvider authenticationProvider;
	 */

	@Autowired
	private UserDetailsService userDetailService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/css/**", "/fonts/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				//.antMatchers("/test").authenticated()
				//.anyRequest().permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.and()
			.logout().permitAll()
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
		//.passwordEncoder(new BCryptPasswordEncoder());
	}
}

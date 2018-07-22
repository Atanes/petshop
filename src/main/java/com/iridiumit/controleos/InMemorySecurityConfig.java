package com.iridiumit.controleos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

//@Configuration
public class InMemorySecurityConfig {
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.inMemoryAuthentication().withUser("carlos").password("12345").roles("USER").and().withUser("atanes")
				.password("12345").roles("USER");
	}
}

package com.example.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;	
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.
				jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}
	
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.inMemoryAuthentication().withUser("Goku").password("{noop}baka").roles("PARTICIPANTE");
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
				.authorizeRequests()
				.antMatchers("/h2/**").permitAll()//H2 database
				.antMatchers("usuario/cadastro").permitAll()
				.antMatchers("/sessoes/**").hasAnyAuthority("USER")
				.antMatchers("/ideias/**").hasAnyAuthority("USER")
				.and().csrf().ignoringAntMatchers("/h2/**")
				.and().headers().frameOptions().sameOrigin()
				.and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/sessoes")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll().and().logout()
				.logoutSuccessUrl("/login");
		
		http.csrf().disable();
	}
}

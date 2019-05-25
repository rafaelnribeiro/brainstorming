package brainstorming.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
//	@Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired NoOpPasswordEncoder noop;
	
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
				.passwordEncoder(noop);
				//.passwordEncoder(bCryptPasswordEncoder);
	}
	
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.inMemoryAuthentication().withUser("User").password("{noop}password").roles("PARTICIPANTE");
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
				.authorizeRequests()
				.antMatchers("/h2/**").permitAll()//H2 database
				.antMatchers("usuario/cadastro").permitAll()
				.antMatchers("/grupos/**").hasAnyAuthority("USER")
				.antMatchers("/sessoes/**").hasAnyAuthority("USER")
				.antMatchers("/ideias/**").hasAnyAuthority("USER")
				.antMatchers("/solicitacoes/**").hasAnyAuthority("USER")
				.and().csrf().ignoringAntMatchers("/h2/**")
				.and().headers().frameOptions().sameOrigin()
				.and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/grupos")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll().and().logout()
				.logoutSuccessUrl("/login");
		
		http.csrf().disable();
	}
}

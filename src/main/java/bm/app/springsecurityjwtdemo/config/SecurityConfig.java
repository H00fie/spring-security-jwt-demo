package bm.app.springsecurityjwtdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final DataSource datasource;
//
//    /**
//     * Configure the database and users.
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .withDefaultSchema()
//                .dataSource(datasource)
//                .withUser("test") //Adding a user for testing purposes.
//                //I need to specify how my password will be encoded. Using the "bcrypt" way, I am encoding "test".
//                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("test"))
//                .roles("USER"); //The user's role.
//    }

    /**
     * HTTP configuration so I can access Swagger and H2 Console while all the other endpoints are secured.
     * A 'csrf' (Cross-Site Request Forgery) is an attack that forces authenticated users to submit a request
     * to a Web application against which they are currently authenticated.
     * It's being disabled because it's not required if a stateless API uses token-based authentication.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("swagger-resources/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }
}

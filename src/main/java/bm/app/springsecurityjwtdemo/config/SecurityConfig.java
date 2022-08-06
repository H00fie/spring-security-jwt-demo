package bm.app.springsecurityjwtdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.sql.DataSource;

/**
 * The first class to be implemented after adding the Spring Security dependency.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Required for the first .configure()
     */
    private final DataSource datasource;

    private final ObjectMapper objectMapper;

    /**
     * To configure the database and users. The below method is sufficient to have the first user
     * with an encoded password and a role of USER.
     * .withDefaultSchema() asks Spring Security to create a required schema. Without it, I am
     * getting an error because "Table USERS not found". I could also add the table myself within
     * my schema.sql file.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .withDefaultSchema()
                .dataSource(datasource)
                .withUser("test") //Adding a user for testing purposes.
                //I need to specify how my password will be encoded. Using the "bcrypt" way, I am encoding "test".
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("test"))
                .roles("USER"); //The user's role.
    }

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
                .formLogin().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
        JsonObjectAuthenticationFilter authenticationFilter = new JsonObjectAuthenticationFilter(objectMapper);
        
        return authenticationFilter;
    }
}

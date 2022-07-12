package bm.app.springsecurityjwtdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource datasource;

    /**
     * Configure the database and users.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(datasource)
                .withUser("test") //Adding a user for testing purposes.
                //I need to specify how my password will be encoded. Using the "bcrypt" way, I am encoding "test".
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("test"))
                .roles("USER"); //The user's role.
    }
}

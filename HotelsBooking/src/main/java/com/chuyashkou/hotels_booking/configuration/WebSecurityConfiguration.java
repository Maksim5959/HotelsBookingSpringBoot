package com.chuyashkou.hotels_booking.configuration;

import com.chuyashkou.hotels_booking.filter.UserSessionFilter;
import com.chuyashkou.hotels_booking.util.DataSourceMappingConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private static final String SQL_USERS_BY_USERNAME_QUERY = "SELECT email, password, is_active FROM users WHERE email=?";
    private static final String SQL_AUTHORITIES_BY_USERNAME_QUERY = "SELECT u.email, ur.roles FROM users u " +
            "INNER JOIN user_role ur ON u.id = ur.user_id WHERE u.email=?";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserSessionFilter userSessionFilter) throws Exception {
        http
                .addFilterAfter(userSessionFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/resources/**", "/styles/**", "/webjars/**");
    }

    @Bean
    public DataSource dataSource(Environment environment) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty(DataSourceMappingConstant.DB_URL.getKey()));
        dataSource.setUsername(environment.getProperty(DataSourceMappingConstant.DB_USER_NAME.getKey()));
        dataSource.setPassword(environment.getProperty(DataSourceMappingConstant.DB_USER_PASSWORD.getKey()));
        return dataSource;
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        return new JdbcUserDetailsManager() {{
            setDataSource(dataSource);
            setUsersByUsernameQuery(SQL_USERS_BY_USERNAME_QUERY);
            setAuthoritiesByUsernameQuery(SQL_AUTHORITIES_BY_USERNAME_QUERY);
        }};
    }
}

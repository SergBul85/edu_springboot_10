package com.hstn.sec.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class MySecurityConfiguration {

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails anna = User.builder()
//                .username("Anna").password("{noop}anna123").roles("USER")
//                .build();
//        UserDetails boris = User.builder()
//                .username("Boris").password("{noop}boris123").roles("USER", "MANAGER")
//                .build();
//        UserDetails victor = User.builder()
//                .username("Victor").password("{noop}victor123").roles("USER", "MANAGER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(anna, boris, victor);
//    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("select client_name, pass, enabled from client where client_name=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select client_name, role from roles where client_name=?");

        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(configurer ->
                        configurer
                                .requestMatchers("/").hasRole("USER")
                                .requestMatchers("/managers/**").hasRole("MANAGER")
                                .requestMatchers("/admins/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(form ->
                        form.loginPage("/myLoginPage")
                                .loginProcessingUrl("/authenticateUser")
                                .permitAll())
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied-page")
                );
        return http.build();
    }

}












package com.hstn.sec.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class MySecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        UserDetails anna = User.builder()
                .username("Anna").password("{noop}anna123").roles("USER")
                .build();
        UserDetails boris = User.builder()
                .username("Boris").password("{noop}boris123").roles("USER", "MANAGER")
                .build();
        UserDetails victor = User.builder()
                .username("Victor").password("{noop}victor123").roles("USER", "MANAGER", "ADMIN")
                .build();


        return new InMemoryUserDetailsManager(anna, boris, victor);
    }

}

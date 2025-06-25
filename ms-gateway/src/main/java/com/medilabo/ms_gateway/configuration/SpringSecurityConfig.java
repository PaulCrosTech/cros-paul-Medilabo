package com.medilabo.ms_gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Spring Security configuration for the application.
 */
@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Value("${ms-patient.security.username}")
    private String username;

    @Value("${ms-patient.security.password}")
    private String password;

    /**
     * Configures the user details service with a single user.
     *
     * @return a MapReactiveUserDetailsService with the configured user
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername(username)
                .password(passwordEncoder().encode(password))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the ServerHttpSecurity object to configure
     * @return the configured SecurityWebFilterChain
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/actuator/**").permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Provides a PasswordEncoder bean for encoding passwords.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

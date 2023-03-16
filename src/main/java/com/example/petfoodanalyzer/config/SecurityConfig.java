package com.example.petfoodanalyzer.config;

import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.repositories.users.UserEntityRepository;
import com.example.petfoodanalyzer.services.users.ApplicationUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserEntityRepository userEntityRepository) {
        return new ApplicationUserDetailsService(userEntityRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests()
                //allow access to static resources
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                //pages with access for all users
                .requestMatchers("/", "/about",
                        "/users/login", "/users/register", "/users/expired", "/users/login-error",
                        "/ingredients/analyze", "/ingredients/all",
                        "/products/all", "/products/details/{id}", "/products/by-brand/{id}").permitAll()
                //pages with access for any authenticated user
                .requestMatchers("/products/favorites", "/products/fave-product/{id}",
                        "/users/my-profile", "/reviews/**").authenticated()
                //pages with access for mod/admin
                .requestMatchers("/products/edit-product/{id}").hasAnyRole(UserRoleTypes.ADMIN.name(), UserRoleTypes.MODERATOR.name())
                //pages with access for admins
                .requestMatchers("/admin", "/admin/**").hasRole(UserRoleTypes.ADMIN.name())
                .anyRequest().authenticated()
                    .and()
                //login handle
                        .formLogin()
                        .loginPage("/users/login")
                        .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                        .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                        .defaultSuccessUrl("/")
                        .failureForwardUrl("/users/login-error")
                    .and()
                //logout handle
                        .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID");

        return http.build();
    }
}

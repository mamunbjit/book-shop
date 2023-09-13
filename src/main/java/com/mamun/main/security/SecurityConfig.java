//package com.mamun.main.security;
//
//
//import com.mamun.main.constants.AppConstants;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import com.mamun.main.security.CustomAuthorizationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManager authenticationManager)
//            throws Exception {
//        http
//                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
//                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth->{
//                    auth
//                            .requestMatchers(HttpMethod.POST, AppConstants.SIGN_IN,AppConstants.SIGN_UP).permitAll()
//                            /*.requestMatchers(HttpMethod.GET,"/users/hello").hasRole("user")
//                            .requestMatchers(HttpMethod.GET,"/users/**").hasRole("admin")*/
//                            .anyRequest().authenticated();
//                })
//                .addFilter(new CustomAuthenticationFilter(authenticationManager))
//                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
//        ;
//        return http.build();
//    }
//
//
//}
//

package com.mamun.main.security;

import com.mamun.main.constants.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;




import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                            .requestMatchers(HttpMethod.GET, "/users/{userId}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/users/{userId}/books").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.GET, "/users/{userId}/borrowed-books").hasAnyRole("ADMIN", "USER")
                            .anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/login");
                })
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}




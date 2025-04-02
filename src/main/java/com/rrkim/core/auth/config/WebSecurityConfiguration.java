package com.rrkim.core.auth.config;

import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.token.JwtAccessDeniedHandler;
import com.rrkim.core.auth.token.JwtAuthenticationEntryPoint;
import com.rrkim.core.auth.token.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {

    @Value("${framework-settings.auth.allowGuest}")
    boolean allowGuest;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(d -> d.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(d -> d.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                         .accessDeniedHandler(jwtAccessDeniedHandler))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        if(allowGuest) {
            httpSecurity.authorizeHttpRequests(d -> d.requestMatchers("/management/**").hasRole(RoleType.ADMIN.name())
                                                     .anyRequest().permitAll());
        } else {
            httpSecurity.authorizeHttpRequests(d -> d.requestMatchers("/").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/documentation").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/file/image/**").permitAll()
                    .requestMatchers("/management/**").hasRole(RoleType.ADMIN.name())
                    .anyRequest().authenticated());
        }

        return httpSecurity.build();
    }

    @Bean(name="PasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

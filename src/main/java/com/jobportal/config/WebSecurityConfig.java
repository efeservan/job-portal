package com.jobportal.config;

import com.jobportal.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    private final String[] publicUrls = {
            "/login",
            "/global-search/**",
            "/register",
            "/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/summernote/**",
            "/js/**",
            "/*.css",
            "/*.js",
            "/*.js.map",
            "/images/**",
            "/fonts**", "/favicon.ico", "/resources/**", "/error"};

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth ->{
            auth.requestMatchers(publicUrls).permitAll();
            auth.anyRequest().authenticated();
        });

        http.formLogin(form->form.loginPage("/login").permitAll()
                .successHandler(customAuthenticationSuccessHandler))
                .logout(logout ->{
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/");
                }).cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> cors.configurationSource(request -> {
            var config = new org.springframework.web.cors.CorsConfiguration();
            config.addAllowedOrigin("http://localhost:8096"); // Statik dosyaların sunulduğu port
            config.addAllowedMethod("*"); // Tüm HTTP metodlarına izin ver
            config.addAllowedHeader("*"); // Tüm başlıklara izin ver
            return config;
        }));

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(customUserDetailsService);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.marvic.factsigner;

import com.marvic.factsigner.security.JwtAuthenticationEntryPoint;
import com.marvic.factsigner.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@SecurityScheme(
//        name = "Bear Authentication",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter){
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }


    /**
     * https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
     *
     * As this methods' signatures clearly say is also stated in the official documentation -
     * antMatcher(String antPattern) - Allows configuring the HttpSecurity to only be invoked when matching the provided ant pattern.
     * mvcMatcher(String mvcPattern) - Allows configuring the HttpSecurity to only be invoked when matching the provided Spring MVC pattern.
     * Generally mvcMatcher is more secure than an antMatcher. As an example:
     * antMatchers("/secured") matches only the exact /secured URL
     * mvcMatchers("/secured") matches /secured as well as /secured/, /secured.html, /secured.xyz
     * and therefore is more general and can also handle some possible configuration mistakes.
     * mvcMatcher uses the same rules that Spring MVC uses for matching (when using @RequestMapping annotation).
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Enable CORS
//        http.csrf().disable()
//                .cors().configurationSource(corsConfigurationSource());

        http
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable()

                .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated()
                        authorize
                                .mvcMatchers("/").permitAll()
                                .mvcMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()

                                //.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                //.requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                                //.requestMatchers("/api/auth/**").permitAll()
                                //.requestMatchers("/swagger-ui/**").permitAll()
                                //.requestMatchers("/v3/api-docs/**").permitAll()
                                //.anyRequest().permitAll()

                ).exceptionHandling( exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                ).sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allowed
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173/"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);

        //the below three lines will add the relevant CORS response headers
        configuration.addAllowedOrigin("http://localhost:5173/");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Used by Spring Security if CORS is enabled.
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

}

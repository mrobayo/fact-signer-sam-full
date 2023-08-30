package com.marvic.factsigner;

import com.marvic.factsigner.security.JwtAuthenticationEntryPoint;
import com.marvic.factsigner.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
//@SecurityScheme(
//        name = "Bear Authentication",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter){
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
     *
     * As this methods' signatures clearly say is also stated in the official documentation -
     *
     * antMatcher(String antPattern) - Allows configuring the HttpSecurity to only be invoked when matching the provided ant pattern.
     *
     * mvcMatcher(String mvcPattern) - Allows configuring the HttpSecurity to only be invoked when matching the provided Spring MVC pattern.
     *
     * Generally mvcMatcher is more secure than an antMatcher. As an example:
     *
     * antMatchers("/secured") matches only the exact /secured URL
     * mvcMatchers("/secured") matches /secured as well as /secured/, /secured.html, /secured.xyz
     * and therefore is more general and can also handle some possible configuration mistakes.
     *
     * mvcMatcher uses the same rules that Spring MVC uses for matching (when using @RequestMapping annotation).
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated()
                        authorize
                                .mvcMatchers("/api/auth/**").permitAll()
                                .mvcMatchers("/usuarios/**").permitAll()
                                .anyRequest().authenticated()

                                //.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                //.requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()

                                //.requestMatchers("/api/auth/**").permitAll()
                                //   .requestMatchers("/swagger-ui/**").permitAll()
                                //   .requestMatchers("/v3/api-docs/**").permitAll()

                                //.anyRequest().permitAll()

                ).exceptionHandling( exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                ).sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

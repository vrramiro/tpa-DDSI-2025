package ar.utn.dssi.FuenteDinamica.config;

import ar.utn.dssi.FuenteDinamica.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer; // <--- 1. IMPORT NECESARIO
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtSecret);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 2. FUNDAMENTAL: Habilita la integración con la config de WebConfig
                .cors(Customizer.withDefaults())

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    // 3. REGLAS PARA CONSUMO PÚBLICO
                    auth.requestMatchers(
                            HttpMethod.GET,
                            "/hechos/**",
                            "/public/**",
                            "/uploads/**" // <--- ESTO permite ver las imágenes sin login
                    ).permitAll();

                    // Opcional: Si quieres que cualquiera suba archivos, deja esto.
                    // Si solo quieres que las VEAN, quita esta línea y deja solo /uploads/**
                    auth.requestMatchers("/multimedia/**").permitAll();

                    auth.anyRequest().authenticated();
                })
                // 4. USAR EL BEAN: Usamos jwtAuthenticationFilter() en lugar de 'new ...'
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
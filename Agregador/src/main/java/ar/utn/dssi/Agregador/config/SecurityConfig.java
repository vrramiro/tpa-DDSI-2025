package ar.utn.dssi.Agregador.config;

import ar.utn.dssi.Agregador.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/hechos",
                            "/hechos/{idHecho}",
                            "/public/**",

                            // A PARTIR DE ACA, LAS RUTAS DEBEN ESTAR PROTEGIDAS... TENGO QUE ARREGLARLO
                            "/colecciones",
                            "/admin/**",
                            "/hecho/{id}/estado",
                            "/hechos/eliminar/{idHecho}",
                            "/procesar/{idSolicitud}"
                    ).permitAll();

                    /*auth.requestMatchers(

                    ).hasAnyRole("ADMINISTRADOR");*/

                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
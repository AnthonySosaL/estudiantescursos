package com.futurewise.futurecourses_backend;

import com.futurewise.futurecourses_backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/auth/registeradmin").hasAuthority("ADMIN_VERIFIED")
                .requestMatchers(HttpMethod.POST, "/api/auth/registerusuario").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/profile").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/profile").authenticated()
                // Solo ADMIN_VERIFIED o PROFESSOR pueden crear cursos y módulos
                .requestMatchers(HttpMethod.POST, "/api/courses/create").hasAnyAuthority("ADMIN_VERIFIED", "PROFESSOR")
                .requestMatchers(HttpMethod.POST, "/api/modules/create").hasAnyAuthority("ADMIN_VERIFIED", "PROFESSOR")
                // Solo autenticados pueden comprar cursos y marcar módulos como completados
                .requestMatchers(HttpMethod.POST, "/api/user-courses/purchase").hasAuthority("CUSTOMER")
                .requestMatchers(HttpMethod.POST, "/api/user-courses/complete-module").authenticated()
                // Consultas públicas de cursos y módulos
                .requestMatchers(HttpMethod.GET, "/api/courses/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/modules/**").permitAll()
                // Consultar progreso solo autenticado
                .requestMatchers(HttpMethod.GET, "/api/user-courses/progress/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtRequestFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    public static class JwtRequestFilter extends OncePerRequestFilter {
        private final JwtUtil jwtUtil;
        public JwtRequestFilter(JwtUtil jwtUtil) { this.jwtUtil = jwtUtil; }
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtUtil.validateToken(token)) {
                    Claims claims = jwtUtil.extractAllClaims(token);
                    String username = claims.getSubject();
                    String role = (String) claims.get("role");
                    boolean verified = Boolean.TRUE.equals(claims.get("verified"));
                    String authority = ("ADMIN".equals(role) && verified) ? "ADMIN_VERIFIED" : role;
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singleton(() -> authority)
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(request, response);
        }
    }
}

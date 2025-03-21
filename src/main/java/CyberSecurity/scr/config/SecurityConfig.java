package CyberSecurity.scr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desativa CSRF se não precisar
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Define rotas públicas
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login // Habilita login via formulário
                        .loginPage("/login") // Página de login personalizada
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll); // Permite logout

        return http.build();
    }
}

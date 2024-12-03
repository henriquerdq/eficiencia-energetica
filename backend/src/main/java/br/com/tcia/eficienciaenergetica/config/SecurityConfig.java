package br.com.tcia.eficienciaenergetica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        		.csrf(AbstractHttpConfigurer::disable)
        		.cors(cors -> cors.configure(httpSecurity))
        		.authorizeHttpRequests(auth -> {
        			auth.anyRequest().permitAll();
        		})
        		.build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    	var config = new CorsConfiguration().applyPermitDefaultValues();
    	var urlCors = new UrlBasedCorsConfigurationSource();
    	urlCors.registerCorsConfiguration("/**", config);
    	return urlCors;
    }

}

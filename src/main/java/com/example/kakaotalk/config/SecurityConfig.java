package com.example.kakaotalk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    /**
     * 비밀번호 암호화용 PasswordEncoder 빈 정의
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // jwt 설정 전 Security 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // REST API 테스트할 땐 CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 인증·인가 정책
                .authorizeHttpRequests(auth -> auth
                        // 회원가입과 사용자 조회는 모두에게 허용
                        .requestMatchers(HttpMethod.POST,   "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET,    "/api/users/**").permitAll()
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // 간단히 Basic Auth(테스트용) 사용. 나중에 JWT 쓰면 제거
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

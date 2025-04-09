package com.shop.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        http.authorizeHttpRequests(
               ar -> ar
                    .requestMatchers("/","/members/**","/items/**")// 요청 매처를 사용하여 요청을 매칭
                    .permitAll() // requestMatchers에 작성된 주소요청에대해 모두 허용 - 인증 노!
                    .anyRequest()  // 모든 요청에 대해
                    .authenticated() // 인증 해야 한다. - 로그인 해야함
            )
            .formLogin(
               form -> form
                    .loginPage("/signIn")  // 커스텀 로그인 페이지 주소
                    .defaultSuccessUrl("/") // 로그인 성공하면 어디로 ?
                    .usernameParameter("userId") //로그인 할때 계정명 input name
                    .failureUrl("/signIn/error")// 로그인 실패시 어디로?
                    .permitAll()  // 로그인 페이지 에 대한 모두가 접근할수 있게 허용
            )
            .logout(out->out
                    .logoutUrl("/logout") // 로그아웃 주소 설정
                    .logoutSuccessUrl("/") // 로그아웃  성공시 이동할 주소
                    .addLogoutHandler(
                            (request, response, authentication) ->
                            {
                                HttpSession session = request.getSession();
                                session.invalidate();
                            }) // 로그아웃하면서  세션 무효화

            );

        //http.formLogin(Customizer.withDefaults());

        return http.build();
    }


}

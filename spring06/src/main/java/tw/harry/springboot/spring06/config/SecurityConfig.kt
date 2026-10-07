package tw.harry.springboot.spring06.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(security: HttpSecurity): SecurityFilterChain {
        /*
            1. 順序的比對，比對到就決定了
                => .anyRequest
            2. hasRole("ADMIN") => ROLE_ADMIN
         */
        security
            // 前後端分離做 authorizeHttpRequests 就好
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    "/login",
                    "/test/**",
                    "/js/**",
                    "/css/**",
                ).permitAll()

                .requestMatchers(
                    "/members/**"
                ).hasAnyRole("ADMIN", "USER")

                .requestMatchers(
                    "/admin/**"
                ).hasRole("ADMIN")

                .anyRequest()
                .authenticated()
            }
            .formLogin { form ->
                form.loginPage("/login")
                    .usernameParameter("account")
                    .passwordParameter("passwd")
                    .loginProcessingUrl("/doLogin")
                    .defaultSuccessUrl("/main")
                    .failureForwardUrl("/login?error")
                    .permitAll()
            }
            .logout { logout ->
                logout.logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies()
            }
            .exceptionHandling { e ->
                e.accessDeniedPage("/page403.html")
//                e.accessDeniedHandler()
            }

        return security.build()
    }
}
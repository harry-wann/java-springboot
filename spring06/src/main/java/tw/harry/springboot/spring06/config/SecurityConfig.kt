package tw.harry.springboot.spring06.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
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
                    .defaultSuccessUrl("/main", true)
                    .failureForwardUrl("/login?error")
                    .permitAll()
            }
            .logout { logout ->
                logout.logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            }
            .exceptionHandling { e ->
                e.accessDeniedPage("/page403")
//                e.accessDeniedHandler()
            }

        return security.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}
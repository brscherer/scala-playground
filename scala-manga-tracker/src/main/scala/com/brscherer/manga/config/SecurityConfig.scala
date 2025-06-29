package com.brscherer.manga.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
class SecurityConfig {

  @Bean
  def securityFilterChain(http: HttpSecurity): SecurityFilterChain = {
    http
      .authorizeHttpRequests(auth => auth
        .requestMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated()
      )
      .csrf(csrf => csrf
        .ignoringRequestMatchers("/h2-console/**")
      )
      .headers(headers => headers
        .frameOptions().sameOrigin()
      )
      .formLogin().and()
      .httpBasic().and()
      .build()
  }
}

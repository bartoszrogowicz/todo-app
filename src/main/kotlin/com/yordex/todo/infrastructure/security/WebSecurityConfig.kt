package com.yordex.todo.infrastructure.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val userDetailsService: DomainUserDetailsService) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers(GET, "/todo**", "/todo/**").hasAuthority("GET_PERMISSION")
            .antMatchers(POST, "/todo**").hasAuthority("ADD_PERMISSION")
            .antMatchers(PUT, "/todo/**").hasAuthority("UPDATE_PERMISSION")
            .and()
            .httpBasic()
            .and()
            .csrf().disable()
    }
}

package com.yordex.todo.infrastructure.security

import com.yordex.todo.domain.user.User
import com.yordex.todo.domain.user.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("userDetailsService")
class DomainUserDetailsService(private val userRepository: UserRepository): UserDetailsService {

    @Transactional
    override fun loadUserByUsername(login: String): UserDetails = userRepository.findUserByLogin(login)
        ?.let {
            createSpringSecurityUser(it)
        } ?: throw UsernameNotFoundException("User: $login not found in database")

    private fun createSpringSecurityUser(user: User): org.springframework.security.core.userdetails.User {
        val grantedAuthorities = user.permissions.map { SimpleGrantedAuthority(it.name) }
        return org.springframework.security.core.userdetails.User(
            user.login,
            user.password,
            grantedAuthorities
        )
    }
}

package com.yordex.todo.domain.user

import org.springframework.data.repository.Repository

interface UserRepository: Repository<User, Long> {

    fun findUserByLogin(login: String): User?
}

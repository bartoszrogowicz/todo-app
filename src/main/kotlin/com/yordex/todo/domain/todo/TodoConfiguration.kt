package com.yordex.todo.domain.todo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class TodoConfiguration(private val todoRepository: TodoRepository) {

    @Bean
    fun todoFacade(todoRepository: TodoRepository) = TodoFacade(todoRepository)

}

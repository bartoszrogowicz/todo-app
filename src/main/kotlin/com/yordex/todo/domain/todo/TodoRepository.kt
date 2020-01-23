package com.yordex.todo.domain.todo

import org.springframework.data.repository.Repository

interface TodoRepository : Repository<Todo, Long> {
    fun findAll(): List<Todo>
    fun findById(id: Long): Todo?
    fun saveAndFlush(todo: Todo): Todo
}

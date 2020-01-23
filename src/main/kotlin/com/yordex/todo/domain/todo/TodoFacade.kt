package com.yordex.todo.domain.todo

import com.yordex.todo.domain.todo.Todo.Companion.fromDto
import com.yordex.todo.domain.todo.dto.TodoDto
import org.springframework.transaction.annotation.Transactional


@Transactional
class TodoFacade(private val todoRepository: TodoRepository) {

    fun findAll(): List<TodoDto> = todoRepository.findAll().map { it.toDto() }

    fun findById(id: Long): TodoDto? = todoRepository.findById(id)?.toDto()

    fun saveTodo(todoDto: TodoDto) = todoRepository.saveAndFlush(fromDto(todoDto))

    fun updateTodo(todoDto: TodoDto) =
            todoRepository.findById(todoDto.id)?.let {
                todoRepository.saveAndFlush(fromDto(todoDto)).toDto()
            }
}

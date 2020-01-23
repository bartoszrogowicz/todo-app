package com.yordex.todo.infrastructure.adapter.input

import com.yordex.todo.domain.todo.TodoFacade
import com.yordex.todo.domain.todo.dto.TodoDto
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/todo")
class TodoController(private val todoFacade: TodoFacade) {

    @GetMapping
    @ResponseStatus(OK)
    fun findAll() = todoFacade.findAll()

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long): ResponseEntity<TodoDto> = todoFacade.findById(id)?.let {
        ResponseEntity.ok(it)
    } ?: ResponseEntity(NOT_FOUND)

    @PostMapping
    @ResponseStatus(CREATED)
    fun addTodo(@Valid @RequestBody todoDto: TodoDto) = todoFacade.saveTodo(todoDto)

    @PutMapping("/{id}")
    fun updateTodo(@PathVariable("id") id: Long, @Valid @RequestBody todoDto: TodoDto): ResponseEntity<TodoDto> {
        val newTodoDto = TodoDto(id, todoDto.name, todoDto.description)
        return todoFacade.updateTodo(newTodoDto)?.let {
            ResponseEntity.ok(it)
        }  ?: ResponseEntity(NOT_FOUND)
    }

}

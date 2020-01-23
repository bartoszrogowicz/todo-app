package com.yordex.todo.domain.todo.dto

data class TodoDto(
        val id: Long,
        val name: String,
        val description: String? = null
)

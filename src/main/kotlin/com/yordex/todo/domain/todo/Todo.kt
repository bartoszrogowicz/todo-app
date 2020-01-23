package com.yordex.todo.domain.todo

import com.yordex.todo.domain.todo.dto.TodoDto
import javax.persistence.*

@Entity
@Table(name = "todo")
data class Todo(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var name: String,
        var description: String? = null
) {
        fun toDto(): TodoDto = TodoDto(id, name, description)

        companion object {
                fun fromDto(todoDto: TodoDto): Todo = Todo(todoDto.id, todoDto.name, todoDto.description)
        }

}

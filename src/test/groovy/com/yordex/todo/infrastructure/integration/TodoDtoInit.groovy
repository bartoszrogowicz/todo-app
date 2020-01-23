package com.yordex.todo.infrastructure.integration


import com.yordex.todo.domain.todo.dto.TodoDto

class TodoDtoInit {

    static TodoDto defaultTodoDto() {
        return new TodoDto(0, "New TODO", "Some Description")
    }
}

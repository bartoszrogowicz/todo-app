package com.yordex.todo.infrastructure.integration

import com.yordex.todo.domain.todo.TodoFacade
import com.yordex.todo.infrastructure.BaseWebMvcSpec
import org.springframework.beans.factory.annotation.Autowired

import static com.yordex.todo.infrastructure.integration.TodoDtoInit.defaultTodoDto
import static org.springframework.http.HttpMethod.PUT
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UpdateTodoIntegrationSpec extends BaseWebMvcSpec implements RequestsBuilder {

    @Autowired
    TodoFacade todoFacade

    def "when authenticated updataTodo with empty body is performed then the response has status 400"() {
        expect: "response status is 400"
        mvc.perform(authUserAll(PUT, "/todo/1").contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest())

    }

    def "when authenticated updateTodo with wrong todoId is performed then the response has status 404"() {
        expect: "response status is 400"
        mvc.perform(authUserAll(PUT, "/todo/1000").contentType("application/json")
                .content("""{
                                    "name": "New Todo Name",
                                    "description": "New Todo Description"  
                                }"""))
                .andExpect(status().isNotFound())

    }

    def "when authenticated updateTodo is performed then the response has status 200 and content is return todo"() {
        given: "save new todo "
        def todo = todoFacade.saveTodo(defaultTodoDto())

        and: "updated todo parameters"
        def updatedTodoName = "Updated Todo Name"
        def updatedTodoDescription = "Updated Todo Description"

        expect: "status is 200 and content is the same as saved todo"
        mvc.perform(authUserAll(PUT, """/todo/$todo.id""").contentType("application/json")
                .content("""{
                                    "name": "$updatedTodoName",
                                    "description": "$updatedTodoDescription"  
                                }"""))
                .andExpect(status().isOk())
                .andExpect(content().json("""{
                                "id": $todo.id,
                                "name": "$updatedTodoName",
                                "description": "$updatedTodoDescription"
                        }"""))

        and: "todo has been updated"
        def updatedTodo = todoFacade.findById(todo.id)
        updatedTodo.id == todo.id
        updatedTodo.name == updatedTodoName
        updatedTodo.description == updatedTodoDescription
    }
}

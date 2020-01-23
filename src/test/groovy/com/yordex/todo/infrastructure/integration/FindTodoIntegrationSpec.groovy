package com.yordex.todo.infrastructure.integration

import com.yordex.todo.domain.todo.TodoFacade
import com.yordex.todo.infrastructure.BaseWebMvcSpec
import org.springframework.beans.factory.annotation.Autowired

import static TodoDtoInit.defaultTodoDto
import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.HttpMethod.GET
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class FindTodoIntegrationSpec extends BaseWebMvcSpec implements RequestsBuilder{

    @Autowired
    TodoFacade todoFacade

    def "when authenticated getById is performed with id which doesn't exist then the response has status 404"() {
        expect: "Status is 404"
        mvc.perform(authUserAll(GET, "/todo/1000")).andExpect(status().isNotFound())
    }

    def "when authenticated getById is performed then the response has status 200 and content is returned todo"() {
        given: "save new todo "
        def todo = todoFacade.saveTodo(defaultTodoDto())

        expect: "status is 200 and content is the same as saved todo"
        mvc.perform(authUserAll(GET, """/todo/$todo.id"""))
                .andExpect(status().isOk())
                .andExpect(content().json("""{
                                "id": $todo.id,
                                "name": "$todo.name",
                                "description": "$todo.description"
                        }"""))
    }

    def "when authenticated getAll is performed then the response has status 200 and content is all tasks"() {
        given: "get all todos from db"
        def todos = todoFacade.findAll()

        expect: "status is 200 and content is all tasks"
        mvc.perform(authUserAll(GET, "/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$.*", hasSize(todos.size())))

    }


}

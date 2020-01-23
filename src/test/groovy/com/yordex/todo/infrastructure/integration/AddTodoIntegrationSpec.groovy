package com.yordex.todo.infrastructure.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.yordex.todo.domain.todo.TodoFacade
import com.yordex.todo.infrastructure.BaseWebMvcSpec
import org.springframework.beans.factory.annotation.Autowired

import static org.springframework.http.HttpMethod.POST
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AddTodoIntegrationSpec extends BaseWebMvcSpec implements RequestsBuilder {

    @Autowired
    TodoFacade todoFacade

    @Autowired
    ObjectMapper objectMapper

    def "when authenticated addTodo with empty body is performed then the response has status 400"() {
        expect: "response status is 400"
        mvc.perform(authUserAll(POST, "/todo").contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest())

    }

    def "when authenticated addTodo with body is performed then the response has status 201"() {
        given: "Todo name and description"
        def newName = "New Todo name"
        def newDescription = "New Todo description"

        expect: "status is 201"
        def result = mvc.perform(authUserAll(POST, "/todo").contentType("application/json")
                .content("""{
                                                        "name": "$newName",
                                                        "description": "$newDescription"
                                                    }"""))
                .andExpect(status().isCreated())
                .andReturn()

        and: "todo has been created"
        def mappedResponse = objectMapper.readValue(result.response.contentAsString, Map)
        def foundTodo = todoFacade.findById(mappedResponse["id"] as Long)
        mappedResponse["id"] == foundTodo.id
        mappedResponse["name"] == foundTodo.name
        mappedResponse["description"] == foundTodo.description
    }


}

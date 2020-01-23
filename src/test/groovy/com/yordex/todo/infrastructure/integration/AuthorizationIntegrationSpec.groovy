package com.yordex.todo.infrastructure.integration

import com.yordex.todo.infrastructure.BaseWebMvcSpec
import spock.lang.Unroll

import static org.springframework.http.HttpMethod.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthorizationIntegrationSpec extends BaseWebMvcSpec implements RequestsBuilder {

    @Unroll
    def "when no authorized request #method #uri is performed then the response has status 401"() {
        expect: ''
        mvc.perform(noAuth(method, uri)).andExpect(status().isUnauthorized())

        where:
        method | uri
        GET    | "/todo/1"
        GET    | "/todo"
        PUT    | "/todo/1"
        POST   | "/todo"
    }

    @Unroll
    def "when #method #uri request with get permissions is performed then the response has status 403"() {
        expect: ''
        mvc.perform(authUserGet(method, uri)).andExpect(status().isForbidden())

        where:
        method | uri
        POST   | "/todo"
        PUT    | "/todo/1"
    }

    def "when PUT request with add permissions is performed then the response has status 403"() {
        expect: ''
        mvc.perform(authUserAdd(PUT, "/todo/1")).andExpect(status().isForbidden())
    }

}

package com.yordex.todo.infrastructure.integration

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

import static org.springframework.util.Base64Utils.encodeToString

trait RequestsBuilder {

    MockHttpServletRequestBuilder noAuth(HttpMethod method, String url) {
        return new MockHttpServletRequestBuilder(method, URI.create(url))
    }

    MockHttpServletRequestBuilder auth(HttpMethod method, String url, String user, String password) {
        return noAuth(method, url).header(HttpHeaders.AUTHORIZATION,
                "Basic " + encodeToString("""$user:$password""".bytes))
    }

    MockHttpServletRequestBuilder authUserAll(HttpMethod method, String url) {
        return auth(method, url, "user_all", "password")
    }

    MockHttpServletRequestBuilder authUserGet(HttpMethod method, String url) {
        return auth(method, url, "user_get", "password")
    }

    MockHttpServletRequestBuilder authUserAdd(HttpMethod method, String url) {
        return auth(method, url, "user_add", "password")
    }

}
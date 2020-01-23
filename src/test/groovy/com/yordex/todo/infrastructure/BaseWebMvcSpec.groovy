package com.yordex.todo.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Testcontainers
abstract class BaseWebMvcSpec extends Specification {

    @Autowired
    MockMvc mvc

}

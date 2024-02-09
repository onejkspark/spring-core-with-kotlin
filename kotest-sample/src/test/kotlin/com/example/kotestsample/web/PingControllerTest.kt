package com.example.kotestsample.web

import com.example.kotestsample.extensions.RestDocExtension
import com.example.kotestsample.extensions.manualRestDocumentation
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.print.print
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

class PingControllerTest : FunSpec() {

    private val converter = MappingJackson2HttpMessageConverter(ObjectMapper())

    private val controller = PingController()

    init {

        extensions(SpringExtension, RestDocExtension())

        beforeTest {  }

        test("test") {

            call(controller, converter)
                .contentType(ContentType.JSON)
                .get("/api/ping")
                .then()
                .statusCode(200)
                .apply(
                    document(
                        "{class-name}/{method-name}",
                        responseFields(
                            PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING).description("상태")
                        )
                    )
                )
                .expect {
                    it.print()
                }
        }
    }
}

suspend fun call(controller: Any, converter: AbstractJackson2HttpMessageConverter): MockMvcRequestSpecification {

    val localMockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .apply<StandaloneMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(
                    manualRestDocumentation()
                )
            )
            .setMessageConverters(converter)
            .alwaysDo<StandaloneMockMvcBuilder>(MockMvcResultHandlers.print())
            .build()

    return RestAssuredMockMvc
        .given()
        .mockMvc(localMockMvc)
}



package com.codesquad.autobid.auth.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

//    @Autowired
//    MockMvc mvc;

    @Test
    @DisplayName("Hello Test")
    void Hello_Test() throws Exception {
        //given
        String hello = "hello";
        //when

        //then
//        mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 GET 요청
//                //mvc.perform()의 결과를 검증
//                .andExpect(status().isOk()) //200 상태
//                .andExpect(content().string(hello)); //응답 본문의 내용을 검증
    }

}

package com.project.shelf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.shelf.MyRestDoc;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf.payment.PortOneService;
import com.project.shelf.user.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    private static ObjectMapper om;
    public static String jwt;

    @MockBean
    private PortOneService portOneService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setup() {
        om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        jwt = AppJwtUtil.create(
                User.builder()
                        .id(1)
                        .email("psk@naver.com")
                        .build());
    }

    @Test
    public void myPage_success_test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(
                get("/app/user/my-page")
                        .header("Authorization", "Bearer " + jwt)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody : " + respBody);

        // then
        actions.andExpect(status().isOk()); // header 검증
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.subPeriod").value("2024.06.10 ~ 2024.07.09"));
        actions.andExpect(jsonPath("$.data.nextPaymentDate").value("2024.07.10"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void myInfo_success_test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(
                get("/app/user/my-info")
                        .header("Authorization", "Bearer " + jwt)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody : " + respBody);

        // then
        actions.andExpect(status().isOk()); // header 검증
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.id").value(1));
        actions.andExpect(jsonPath("$.data.avatar").value("AVATAR01"));
        actions.andExpect(jsonPath("$.data.nickName").value("박선규"));
        actions.andExpect(jsonPath("$.data.email").value("psk@naver.com"));
        actions.andExpect(jsonPath("$.data.password").value("1234"));
        actions.andExpect(jsonPath("$.data.phone").value("010-2897-2345"));
        actions.andExpect(jsonPath("$.data.address").value("부산광역시 금정구"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void wishList_success_test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(
                get("/app/user/my-library")
                        .header("Authorization", "Bearer " + jwt)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody : " + respBody);

        // then
        actions.andExpect(status().isOk()); // header 검증
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.bookList[0].historyList[0].id").value(1));
        actions.andExpect(jsonPath("$.data.bookList[0].historyList[0].imagePath").value("/image/book/대화의_힘.jpg"));
        actions.andExpect(jsonPath("$.data.bookList[0].historyList[0].bookTitle").value("대화의 힘"));
        actions.andExpect(jsonPath("$.data.bookList[0].historyList[0].pageCount").value(300));
        actions.andExpect(jsonPath("$.data.bookList[0].historyList[0].lastReadPage").value(300));
        actions.andExpect(jsonPath("$.data.bookList[0].allBook[0].id").value(1));
        actions.andExpect(jsonPath("$.data.bookList[0].allBook[0].bookImagePath").value("/image/book/대화의_힘.jpg"));
        actions.andExpect(jsonPath("$.data.bookList[0].allBook[0].bookTitle").value("대화의 힘"));
        actions.andExpect(jsonPath("$.data.bookList[0].allBook[0].author").value("찰스 두히그"));

        actions.andExpect(jsonPath("$.data.wishList[0].id").value(1));
        actions.andExpect(jsonPath("$.data.wishList[0].bookImagePath").value("/image/book/대화의_힘.jpg"));
        actions.andExpect(jsonPath("$.data.wishList[0].bookId").value(1));
        actions.andExpect(jsonPath("$.data.wishList[0].bookTitle").value("대화의 힘"));
        actions.andExpect(jsonPath("$.data.wishList[0].author").value("찰스 두히그"));
        actions.andExpect(jsonPath("$.data.wishList[0].createdAt").value("2024-07-01"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


}

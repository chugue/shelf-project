package com.project.shelf.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.shelf.MyRestDoc;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf.payment.PortOneService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    private ObjectMapper om = new ObjectMapper();
    private static String jwt;

    @MockBean
    private PortOneService portOneService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setup() {
        jwt = AppJwtUtil.create(
                User.builder()
                        .id(1)
                        .email("psk@naver.com")
                        .build());
    }

    //메인
    @Test
    public void mainPage_test_success() throws Exception {
        // given


        // when
        ResultActions actions = mvc.perform(get("/app/main")
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(jsonPath("$.status").value("200")); // header 검증
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.bestSellerDTOS[0].id").value(1));
        actions.andExpect(jsonPath("$.data.bestSellerDTOS[0].bookImagePath").value("/image/book/대화의_힘.jpg"));
        actions.andExpect(jsonPath("$.data.bestSellerDTOS[0].bookTitle").value("대화의 힘"));
        actions.andExpect(jsonPath("$.data.bestSellerDTOS[0].author").value("찰스 두히그"));
        actions.andExpect(jsonPath("$.data.bestSellerDTOS[0].rankNum").value(1));

        actions.andExpect(jsonPath("$.data.weekBestSellerDTOS[0].id").value(6));
        actions.andExpect(jsonPath("$.data.weekBestSellerDTOS[0].bookImagePath").value("/image/book/만들면서_배우는_플러터_앱_프로그래밍.jpg"));
        actions.andExpect(jsonPath("$.data.weekBestSellerDTOS[0].bookTitle").value("만들면서 배우는 플러터 앱 프로그래밍"));
        actions.andExpect(jsonPath("$.data.weekBestSellerDTOS[0].author").value("최주호"));
        actions.andExpect(jsonPath("$.data.weekBestSellerDTOS[0].rankNum").value(1));

        actions.andExpect(jsonPath("$.data.dayBestSellerDTO.id").value(6));
        actions.andExpect(jsonPath("$.data.dayBestSellerDTO.bookImagePath").value("/image/book/만들면서_배우는_플러터_앱_프로그래밍.jpg"));
        actions.andExpect(jsonPath("$.data.dayBestSellerDTO.bookTitle").value("만들면서 배우는 플러터 앱 프로그래밍"));
        actions.andExpect(jsonPath("$.data.dayBestSellerDTO.author").value("최주호"));

        actions.andExpect(jsonPath("$.data.bookHistoryDTOS[0].userId").value(1));
        actions.andExpect(jsonPath("$.data.bookHistoryDTOS[0].bookId").value(1));
        actions.andExpect(jsonPath("$.data.bookHistoryDTOS[0].bookTitle").value("대화의 힘"));
        actions.andExpect(jsonPath("$.data.bookHistoryDTOS[0].pageCount").value(300));
        actions.andExpect(jsonPath("$.data.bookHistoryDTOS[0].lastReadPage").value(300));
        actions.andExpect(jsonPath("$.data.bookHistoryDTOS[0].bookImagePath").value("/image/book/대화의_힘.jpg"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
}

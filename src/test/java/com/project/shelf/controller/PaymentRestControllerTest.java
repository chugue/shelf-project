package com.project.shelf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.shelf.MyRestDoc;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf.payment.PaymentRequestRecord.PaymentSaveReqDTO;
import com.project.shelf.payment.PaymentRequestRecord.PaymentUnscheduleDTO;
import com.project.shelf.payment.PortOneService;
import com.project.shelf.user.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PaymentRestControllerTest extends MyRestDoc {
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
    public void paymentList_success_test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(
                get("/app/payment-list")
                        .header("Authorization", "Bearer " + jwt)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody : " + respBody);

        // then
        actions.andExpect(status().isOk()); // header 검증
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.paymentListCount").value(2));
        actions.andExpect(jsonPath("$.data.paymentDTOList[0].paymentId").value(1));
        actions.andExpect(jsonPath("$.data.paymentDTOList[0].subTypePeriod").value("일개월"));
        actions.andExpect(jsonPath("$.data.paymentDTOList[0].paymentStatus").value("완료"));
        actions.andExpect(jsonPath("$.data.paymentDTOList[0].paymentAt").value("2024-05-10"));
        actions.andExpect(jsonPath("$.data.paymentDTOList[0].amount").value("10,000"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
    @Test
    public void paymentList_fail_test() throws Exception { // 로그인 안 했을 때
        // given

        // when
        ResultActions actions = mvc.perform(
                get("/app/payment-list")
                        .header("Authorization", "Bearer ")
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody : " + respBody);

        // then
        actions.andExpect(jsonPath("$.status").value(401));
        actions.andExpect(jsonPath("$.msg").value("토큰이 유효하지 않습니다."));
        actions.andExpect(jsonPath("$.data").isEmpty());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @Test
    public void pay_success_test() throws Exception {
        // given
        PaymentSaveReqDTO reqDTO = PaymentSaveReqDTO.builder()
                .impUid("imp_999237098237")
                .payMethod("card")
                .merchantUid("order_1721350232046")
                .name("1개월 정기 결제")
                .paidAmount(10000)
                .status("완료")
                .paidAt("172135289")
                .cardName("신한카드")
                .customerUid("customer_1721350232047")
                .buyerEmail("psk@naver.com")
                .build();
        String reqBody = om.writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                post("/app/pay")
                        .header("Authorization", "Bearer " + jwt)
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody : " + respBody);

        // then
        actions.andExpect(status().isOk()); // header 검증
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data").value("결제가 완료되었습니다."));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @Test
    public void pay_fail_test() throws Exception { // 사용자 이메일이 없을 때
        // given
        PaymentSaveReqDTO reqDTO = PaymentSaveReqDTO.builder()
                .impUid("imp_999237098237")
                .payMethod("card")
                .merchantUid("order_1721350232046")
                .name("1개월 정기 결제")
                .paidAmount(10000)
                .status("완료")
                .paidAt("172135289")
                .cardName("신한카드")
                .customerUid("customer_1721350232047")
                .buyerEmail("")
                .build();
        String reqBody = om.writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                post("/app/pay")
                        .header("Authorization", "Bearer " + jwt)
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody : " + respBody);

        // then
        actions.andExpect(jsonPath("$.status").value(401));
        actions.andExpect(jsonPath("$.msg").value("존재하지 않는 회원입니다."));
        actions.andExpect(jsonPath("$.data").isEmpty());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @Test
    public void unschedule_success_test() throws Exception {
        // given
        PaymentUnscheduleDTO reqDTO = PaymentUnscheduleDTO.builder()
                .userId(1)
                .build();
        String reqBody = om.writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                post("/app/unschedule")
                        .header("Authorization", "Bearer " + jwt)
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody : " + respBody);

        // then
        actions.andExpect(status().isOk()); // header 검증
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data").value("이용권이 해지되었습니다."));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @Test
    public void unschedule_fail_test() throws Exception { // 존재하지 않는 user의 id를 전달한 경우
        // given
        PaymentUnscheduleDTO reqDTO = PaymentUnscheduleDTO.builder()
                .userId(100)
                .build();
        String reqBody = om.writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                post("/app/unschedule")
                        .header("Authorization", "Bearer " + jwt)
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody : " + respBody);

        // then
        actions.andExpect(jsonPath("$.status").value(401));
        actions.andExpect(jsonPath("$.msg").value("존재하지 않는 회원입니다."));
        actions.andExpect(jsonPath("$.data").isEmpty());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
}
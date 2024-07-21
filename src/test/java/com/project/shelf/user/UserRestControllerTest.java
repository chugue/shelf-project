package com.project.shelf.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shelf.payment.PortOneService;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class UserRestControllerTest {


    @MockBean
    private PortOneService portOneService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    // 회원가입 테스트

    @Test
    public void join_test() throws Exception {
        // given
        System.out.println("💕💕💕💕💕테스트 실행");
        String email    = "matthew@gmail.com";
        String nickName = "matthew";
        String password = "1234";

        UserRequest.JoinDTO reqDTO = new UserRequest.JoinDTO(email, nickName, password);
        System.out.println("요청 DTO : " + reqDTO.toString());

        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);
        System.out.println("요청 바디 : " + reqBody);

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/join")
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        int statusCode = actions.andReturn().getResponse().getStatus();
        System.out.println("응답 바디 : " + respBody);
        System.out.println("상태 코드 : " + statusCode);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(11));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("matthew@gmail.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.nickName").value("matthew"));
    }

    // 이메일 중복 확인 테스트 ( 중복 X )
    @Test
    public void checkEmailDupFalse_test() throws Exception {
        // given
        System.out.println("💕💕💕💕💕이메일 중복 확인 테스트 실행💕💕💕💕💕");
        String email    = "unique@email.com";

        when(userService.checkEmailDuplicate(email)).thenReturn(false);

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/user/check-email")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        int statusCode = actions.andReturn().getResponse().getStatus();

        System.out.println("응답 바디 : " + respBody);
        System.out.println("상태 코드 : " + statusCode);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("중복되지 않은 이메일입니다."));
    }

    // 이메일 중복 확인 테스트 ( 중복 O )
    @Test
    public void checkEmailDupTrue_test() throws Exception {
        // given
        System.out.println("💕💕💕💕💕이메일 중복 확인 테스트 실행💕💕💕💕💕");
        String email    = "ysh@naver.com";

        when(userService.checkEmailDuplicate(email)).thenReturn(true);

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/user/check-email")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        int statusCode = actions.andReturn().getResponse().getStatus();

        System.out.println("응답 바디 : " + respBody);
        System.out.println("상태 코드 : " + statusCode);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("중복된 이메일입니다."));
    }

    // 닉네임 중복 테스트 ( 중복 X )
    @Test
    public void checkNickNameDupFalse_test() throws Exception {
        // given
        System.out.println("💕💕💕💕💕닉네임 중복 확인 테스트 실행💕💕💕💕💕");
        String nickName    = "unique";

        when(userService.checkNickNameDuplicate(nickName)).thenReturn(false);

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/user/check-nickName")
                        .param("nickName", nickName)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        int statusCode = actions.andReturn().getResponse().getStatus();
        System.out.println("응답 바디 : " + respBody);
        System.out.println("상태 코드 : " + statusCode);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("중복되지 않은 닉네임입니다."));
    }
    // 닉네임 중복 테스트 ( 중복 O )
    @Test
    public void checkNickNameDupTrue_test() throws Exception {
        // given
        System.out.println("💕💕💕💕💕닉네임 중복 확인 테스트 실행💕💕💕💕💕");
        String nickName    = "ysh";

        // when
        when(userService.checkNickNameDuplicate(nickName)).thenReturn(true);

        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/user/check-nickName")
                        .param("nickName", nickName)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        int statusCode = actions.andReturn().getResponse().getStatus();

        System.out.println("응답 바디 : " + respBody);
        System.out.println("상태 코드 : " + statusCode);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("중복된 닉네임입니다."));

        System.out.println("💕💕💕💕💕닉네임 중복 확인 테스트 종료💕💕💕💕💕");
    }

    // 로그인 테스트
    @Test
    public void login_test() throws Exception {
        // given
        System.out.println("💕💕💕💕💕테스트 실행");
        String email    = "ysh@naver.com";
        String password = "1234";

        LoginReqDTO reqDTO = new LoginReqDTO(email, password);
        System.out.println("요청 DTO : " + reqDTO.toString());

        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);
        System.out.println("요청 바디 : " + reqBody);

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login")
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        int statusCode = actions.andReturn().getResponse().getStatus();
        System.out.println("응답 바디 : " + respBody);
        System.out.println("상태 코드 : " + statusCode);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(6));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("ysh@naver.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.nickName").value("ysh"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.avatar").value("AVATAR06"));
    }

    // Naver OAuth 테스트
    @Test
    public void oauthCallback_test() throws Exception {
        // given
        System.out.println("💕💕💕💕💕Naver OAouth 테스트 실행💕💕💕💕💕");
        String NaverAccessToken = "몰라";
        String shelfAccessToken = "몰라";


        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/oauth/naver/callback")
                        .content("accessToken=" + NaverAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye


        // then
//        actions.andExpect(MockMvcResultMatchers.status().isOk());
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(6));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("ysh@naver.com"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.nickName").value("ysh"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.avatar").value("AVATAR06"));
    }
}

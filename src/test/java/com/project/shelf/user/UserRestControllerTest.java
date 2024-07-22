package com.project.shelf.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shelf.MyRestDoc;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf.payment.PortOneService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import com.project.shelf._core.enums.Avatar;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

    @MockBean
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

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
                post("/user/join")
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
                post("/user/login")
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

    // 사용자 개인정보 업데이트
    @Test
    public void updateInfo_test() throws Exception {
        System.out.println("💕💕💕💕💕사용자 개인정보 업데이트 테스트 실행💕💕💕💕💕");
        // given
        // JWT
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJibG9nIiwiaWQiOjEsImV4cCI6MTcyMTY2MDYwM30.UAGYr7z57RImWSP9P_s9mk8HrCEtg7aw3Nx7tbSjG69yqEJLGS6BJ0esYnEiTspFsB10XBdr_I6Tvg0n6GcizQ"; // 예시로 사용되는 JWT 토큰

        // Session
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(1);

        // Request Body
        UserRequest.UpdateInfoDTO reqDTO = new UserRequest.UpdateInfoDTO();
        reqDTO.setAvatar(Avatar.valueOf("AVATAR02"));
        reqDTO.setNickName("matthew");
        reqDTO.setPassword("4321");
        reqDTO.setPhone("010-9603-2291");
        reqDTO.setAddress("부산 진구 가야동");
        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);

        System.out.println("세션유저" + sessionUser);
        System.out.println("/ 요청 바디 : " + reqBody);

        // Response DTO
        UserResponse.UpdateInfoDTO respDTO = new UserResponse.UpdateInfoDTO();
        respDTO.setAvatar(reqDTO.getAvatar());
        respDTO.setNickName(reqDTO.getNickName());
        respDTO.setPassword(reqDTO.getPassword());
        respDTO.setPhone(reqDTO.getPhone());
        respDTO.setAddress(reqDTO.getAddress());

        // userService.UpdateInfo 특정값 반환 ( respDTO )
        when(userService.UpdateInfo(any(SessionUser.class),any(UserRequest.UpdateInfoDTO.class))).thenReturn(respDTO);

        ResultActions actions = mockMvc.perform(
                post("/app/user/update-info")
                        .header("Authorization", "Bearer " + jwt) // JWT 헤더에 추가
                        .sessionAttr("sessionUser", sessionUser)
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

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.avatar").value("AVATAR02"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.nickName").value("matthew"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value("4321"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.phone").value("010-9603-2291"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value("부산 진구 가야동"));

        System.out.println("💕💕💕💕💕사용자 개인정보 업데이트 테스트 종료💕💕💕💕💕");
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
                post("/oauth/naver/callback")
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

    //메인
    @Test
    public void mainPage_test_success() throws Exception {
        // given


        // when
        ResultActions actions = mvc.perform(get("/app/main")
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
//        System.out.println("respBody = " + respBody);
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

    //메인 실패 테스트
    @Test
    public void mainPage_test_fail() throws Exception {
        // given


        // when
        ResultActions actions = mvc.perform(get("/app/main")
                .header("Authorization", "Bearer "));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(jsonPath("$.status").value("401")); // header 검증
        actions.andExpect(jsonPath("$.msg").value("토큰이 유효하지 않습니다."));
        actions.andExpect(jsonPath("$.data").isEmpty());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
}

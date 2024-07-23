package com.project.shelf.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {
    private static ObjectMapper om;
    private static String jwt;

    @MockBean
    private PortOneService portOneService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    // 회원가입 테스트
    @Test
    public void join_success_test() throws Exception {
        // given
        String email    = "matthew@gmail.com";
        String nickName = "matthew";
        String password = "1234";

        UserRequest.JoinDTO reqDTO = new UserRequest.JoinDTO(email, nickName, password);
        System.out.println("요청 DTO : " + reqDTO.toString());

        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);
        System.out.println("요청 바디 : " + reqBody);

        // when
        ResultActions actions = mvc.perform(
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
        
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 회원가입 테스트 ( FAIL )
    @Test
    public void join_fail_test() throws Exception {
        // given
        String email    = "matthew@naver.com";
        String nickName = "matthew";
        String password = "123";

        UserRequest.JoinDTO reqDTO = new UserRequest.JoinDTO(email, nickName, password);
        System.out.println("요청 DTO : " + reqDTO.toString());

        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);
        System.out.println("요청 바디 : " + reqBody);

        // when
        ResultActions actions = mvc.perform(
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
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("비밀번호는 4자 이상, 20자 이하여야 합니다! : password"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist());

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 이메일 중복 확인 테스트 ( 중복 X )
    @Test
    public void checkEmailDup_false_test() throws Exception {
        // given
        String email = "unique@email.com";

        // when
        ResultActions actions = mvc.perform(
                get("/user/check-email")
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

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 이메일 중복 확인 테스트 ( 중복 O )
    @Test
    public void checkEmailDup_success_test() throws Exception {
        // given
        String email = "ysh@naver.com";

        // when
        ResultActions actions = mvc.perform(
                get("/user/check-email")
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

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 닉네임 중복 테스트 ( 중복 X )
    @Test
    public void checkNickNameDup_fail_test() throws Exception {
        // given
        String nickName = "unique";

        // when
        ResultActions actions = mvc.perform(
                get("/user/check-nickName")
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

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 닉네임 중복 테스트 ( 중복 O )
    @Test
    public void checkNickNameDup_success_test() throws Exception {
        // given
        String nickName = "ysh";

        // when
        ResultActions actions = mvc.perform(
                get("/user/check-nickName")
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

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 로그인 테스트
    @Test
    public void login_success_test() throws Exception {
        // given
        String email    = "ysh@naver.com";
        String password = "1234";

        LoginReqDTO reqDTO = new LoginReqDTO(email, password);
        System.out.println("요청 DTO : " + reqDTO.toString());

        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);
        System.out.println("요청 바디 : " + reqBody);

        // when
        ResultActions actions = mvc.perform(
                post("/user/login")
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        int statusCode = actions.andReturn().getResponse().getStatus();
        String jwt = actions.andReturn().getResponse().getHeader("Authorization");
        System.out.println("응답 바디 : " + respBody);
        System.out.println("상태 코드 : " + statusCode);
        System.out.println("jwt : " + jwt);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(6));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("ysh@naver.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.nickName").value("ysh"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.avatar").value("AVATAR06"));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 로그인 테스트 ( FAIL )
    @Test
    public void login_fail_test() throws Exception {
        // given
        String email    = "ysh@naver.com";
        String password = null;

        LoginReqDTO reqDTO = new LoginReqDTO(email, password);
        System.out.println("요청 DTO : " + reqDTO.toString());

        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);
        System.out.println("요청 바디 : " + reqBody);

        // when
        ResultActions actions = mvc.perform(
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
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("비밀번호를 입력해주세요. : password"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist());

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 사용자 개인정보 업데이트
    @Test
    public void updateInfo_success_test() throws Exception {
        // given

        // Request Body
        UserRequest.UpdateInfoDTO reqDTO = new UserRequest.UpdateInfoDTO();
        reqDTO.setAvatar(Avatar.valueOf("AVATAR02"));
        reqDTO.setNickName("matthew");
        reqDTO.setPassword("4321");
        reqDTO.setPhone("010-9603-2291");
        reqDTO.setAddress("부산 진구 가야동");
        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);

        System.out.println("/ 요청 바디 : " + reqBody);

        // when
        ResultActions actions = mvc.perform(
                post("/app/user/update-info")
                        .header("Authorization", "Bearer " + jwt) // JWT 헤더에 추가
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

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 사용자 개인정보 업데이트 ( FAIL )
    @Test
    public void updateInfo_fail_test() throws Exception {
        // given

        // Request Body
        UserRequest.UpdateInfoDTO reqDTO = new UserRequest.UpdateInfoDTO();
        reqDTO.setAvatar(Avatar.valueOf("AVATAR02"));
        reqDTO.setNickName("matthew");
        reqDTO.setPassword("4321");
        reqDTO.setPhone("01096032291");
        reqDTO.setAddress("부산 진구 가야동");
        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);

        System.out.println("/ 요청 바디 : " + reqBody);

        // when
        ResultActions actions = mvc.perform(
                post("/app/user/update-info")
                        .header("Authorization", "Bearer " + jwt) // JWT 헤더에 추가
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        int statusCode = actions.andReturn().getResponse().getStatus();
        System.out.println("응답 바디 : " + respBody);
        System.out.println("상태 코드 : " + statusCode);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("전화번호는 '000-0000-0000' 형식이어야 합니다. : phone"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist());

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

//    // Naver OAuth 테스트
//    @Test
//    public void oauthCallback_test() throws Exception {
//        // given
//        System.out.println("💕💕💕💕💕Naver OAuth 테스트 실행💕💕💕💕💕");
//        String NaverAccessToken = "";
//        String shelfAccessToken = "";
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                post("/oauth/naver/callback")
//                        .content("accessToken=" + NaverAccessToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        // eye
//
//
//        // then
//        actions.andExpect(MockMvcResultMatchers.status().isOk());
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(6));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("ysh@naver.com"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.nickName").value("ysh"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.avatar").value("AVATAR06"));
//    }

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
    public void myPage_fail_test() throws Exception { // 로그인 안 했을 때
        // given

        // when
        ResultActions actions = mvc.perform(
                get("/app/user/my-page")
                        .header("Authorization", "Bearer " )
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
    public void myInfo_fail_test() throws Exception { // 로그인 안 했을 때
        // given

        // when
        ResultActions actions = mvc.perform(
                get("/app/user/my-info")
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
    @Test
    public void wishList_fail_test() throws Exception { // 로그인 안 했을 때
        // given

        // when
        ResultActions actions = mvc.perform(
                get("/app/user/my-library")
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
}

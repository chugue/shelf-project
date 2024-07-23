package com.project.shelf.wishlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shelf.MyRestDoc;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf.payment.PortOneService;
import com.project.shelf.user.*;
import com.project.shelf.wishlist.WishlistRequestRecord.WishlistSaveReqDTO;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class WishlistRestControllerTest extends MyRestDoc {
    private ObjectMapper om = new ObjectMapper();
    private static String jwt;

    @MockBean
    private PortOneService portOneService;

    @BeforeAll
    public static void setup() {
        jwt = AppJwtUtil.create(
                User.builder()
                        .id(1)
                        .email("psk@naver.com")
                        .build());
    }

    //위시 리스트 삭제
    @Test
    public void wishlist_remove_success_test() throws Exception {
        // given
        Integer userId = 1;
        Integer bookId = 1;

        WishlistSaveReqDTO reqDTO = new WishlistSaveReqDTO(userId, bookId);
        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);


        // when
        ResultActions actions = mvc.perform(
                post("/app/wishlist/toggle")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody: "+respBody);

        // then
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.userId").value(1));
        actions.andExpect(jsonPath("$.data.bookId").value(1));
        actions.andExpect(jsonPath("$.data.createdAt").exists());
        actions.andExpect(jsonPath("$.data.updatedAt").isEmpty());

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //위시 리스트 삭제 실패
    @Test
    public void wishlist_remove_fail_test() throws Exception {
        // given
        Integer userId = 999;
        Integer bookId = 1;

        WishlistSaveReqDTO reqDTO = new WishlistSaveReqDTO(userId, bookId);
        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                post("/app/wishlist/toggle")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody: "+respBody);

        // then
        actions.andExpect(jsonPath("$.status").value(401));
        actions.andExpect(jsonPath("$.msg").value("사용자 정보를 찾을 수 없습니다."));
        actions.andExpect(jsonPath("$.data").isEmpty());

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //위시 리스트 저장
    @Test
    public void wishlist_add_success_test() throws Exception {
        // given
        Integer userId = 3;
        Integer bookId = 1;

        WishlistSaveReqDTO reqDTO = new WishlistSaveReqDTO(userId, bookId);
        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                post("/app/wishlist/toggle")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody: "+respBody);

        // then
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.userId").value(3));
        actions.andExpect(jsonPath("$.data.bookId").value(1));
        actions.andExpect(jsonPath("$.data.createdAt").exists());
        actions.andExpect(jsonPath("$.data.updatedAt").isEmpty());

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 위시 리스트 저장 실패
    @Test
    public void wishlist_add_fail_test() throws Exception {
        // given
        Integer userId = 999;
        Integer bookId = 1;

        WishlistSaveReqDTO reqDTO = new WishlistSaveReqDTO(userId, bookId);


        String reqBody = new ObjectMapper().writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                post("/app/wishlist/toggle")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody)
        );

        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("respBody: "+respBody);

        // then
        actions.andExpect(jsonPath("$.status").value(401));
        actions.andExpect(jsonPath("$.msg").value("사용자 정보를 찾을 수 없습니다."));
        actions.andExpect(jsonPath("$.data").isEmpty());

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 위시 리스트 업데이트 확인
    @Test
    public void get_books_success_test() throws Exception {
        // given
        Integer bookId = 1;

        // when
        ResultActions actions = mvc.perform(get("/app/books/"+bookId)
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(jsonPath("$.status").value("200")); // header 검증
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.id").value(1));
        actions.andExpect(jsonPath("$.data.bookId").value(1));
        actions.andExpect(jsonPath("$.data.bookImagePath").value("/image/book/대화의_힘.jpg"));
        actions.andExpect(jsonPath("$.data.bookTitle").value("대화의 힘"));
        actions.andExpect(jsonPath("$.data.author").value("찰스 두히그"));
        actions.andExpect(jsonPath("$.data.createdAt").exists());

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 위시 리스트 업데이트 실패
    @Test
    public void get_books_fail_test() throws Exception {
        // given
        Integer bookId = 999 ;

        // when
        ResultActions actions = mvc.perform(get("/app/books/"+bookId)
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(jsonPath("$.status").value(404)); // header 검증
        actions.andExpect(jsonPath("$.msg").value("도서를 찾을 수 없습니다."));
        actions.andExpect(jsonPath("$.data").isEmpty());

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}

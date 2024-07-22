package com.project.shelf.book;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //몫으로 가짜 서버 띄우기
public class BookRestControllerTest extends MyRestDoc {
    private static String jwt;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @MockBean
    private PortOneService portOneService;

    @BeforeAll
    public static void setUp() {
        jwt = AppJwtUtil.create(
                User.builder()
                        .id(1)
                        .email("psk@naver.com")
                        .nickName("박선규")
                        .build());
    }

    //책 신간 페이지
    @Test
    public void brandNew_success_test() throws Exception {
        // given
        String month = "2024-06";
        // when
        ResultActions actions = mvc.perform(get("/app/book/new")
                .param("month" ,month)
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(status().isOk()); // header 검증
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data[0].brandNewWeekly").value("첫째"));
        actions.andExpect(jsonPath("$.data[0].brandNewList[0].bookId").value(11));
        actions.andExpect(jsonPath("$.data[0].brandNewList[0].title").value("컬처, 문화로 쓴 세계사"));
        actions.andExpect(jsonPath("$.data[0].brandNewList[0].author").value("마틴 푸크너"));
        actions.andExpect(jsonPath("$.data[0].brandNewList[0].path").value("/image/book/컬처_문화로쓴세계사.jpg"));
        actions.andExpect(jsonPath("$.errorMessage").doesNotExist());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    //책 검색
    @Test
    public void searchBook_test_success() throws Exception {
        // given
        String category = "소설";
        String author = "알렉스 안도릴";
        // when
        ResultActions actions = mvc.perform(get("/app/book/search")
                .param("category" ,category)
                .param("author" ,author)
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(jsonPath("$.status").value("200")); // header 검증
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.bookCount").value(1));
        actions.andExpect(jsonPath("$.data.book[0].bookId").value(3));
        actions.andExpect(jsonPath("$.data.book[0].bookTitle").value("아이가 없는 집"));
        actions.andExpect(jsonPath("$.data.book[0].author.authorId").value(3));
        actions.andExpect(jsonPath("$.data.book[0].author.authorName").value("알렉스 안도릴"));
        actions.andExpect(jsonPath("$.data.book[0].bookPath").value("/image/book/아버지의_해방일지.jpg"));
        actions.andExpect(jsonPath("$.errorMessage").doesNotExist());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    //책 상세보기
    @Test
    public void bookDetail_test_success() throws Exception {
        // given
        Integer bookId = 1;
        // when
        ResultActions actions = mvc.perform(get("/app/book/"+bookId)
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(jsonPath("$.status").value("200")); // header 검증
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.id").value(1));
        actions.andExpect(jsonPath("$.data.path").value("/image/book/대화의_힘.jpg"));
        actions.andExpect(jsonPath("$.data.title").value("대화의 힘"));
        actions.andExpect(jsonPath("$.data.author.id").value(1));
        actions.andExpect(jsonPath("$.data.author.name").value("찰스 두히그"));
        actions.andExpect(jsonPath("$.data.author.createdAt").exists());
        actions.andExpect(jsonPath("$.data.author.updatedAt").exists());
        actions.andExpect(jsonPath("$.data.author.authorIntro").value("퓰리처상 수상 저널리스트이자 미국 최고의 논픽션 작가"));
        actions.andExpect(jsonPath("$.data.publisher").value("선규사"));
        actions.andExpect(jsonPath("$.data.category").value("자기계발"));
        actions.andExpect(jsonPath("$.data.createdAt").exists());
        actions.andExpect(jsonPath("$.data.bookIntro").value("대화의 중요성과 기술을 다룬 책입니다."));
        actions.andExpect(jsonPath("$.data.contentIntro").value("대화의 힘을 통해 원하는 것을 얻는 방법을 설명합니다."));
        actions.andExpect(jsonPath("$.data.isWish").value(true));
        actions.andExpect(jsonPath("$.data.registrationDate").value("2024-06-25"));
        actions.andExpect(jsonPath("$.data.totalViews").value(527));
        actions.andExpect(jsonPath("$.data.completedViews").value(300));
        actions.andExpect(jsonPath("$.errorMessage").doesNotExist());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    //책 상세보기 실패 테스트
    @Test
    public void bookDetail_test_fail() throws Exception {
        // given
        Integer bookId = 999;
        // when
        ResultActions actions = mvc.perform(get("/app/book/"+bookId)
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(jsonPath("$.status").value("401")); // header 검증
        actions.andExpect(jsonPath("$.msg").value("책 정보를 찾을 수 없습니다!!"));
        actions.andExpect(jsonPath("$.data").isEmpty());
        actions.andExpect(jsonPath("$.errorMessage").doesNotExist());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    //카테고리별 랭크
    @Test
    public void rankBook_test_success() throws Exception {
        // given
        String category = "소설";
        // when
        ResultActions actions = mvc.perform(get("/app/book/rank")
                .param("category" ,category)
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(jsonPath("$.status").value("200")); // header 검증
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].id").value(1));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].bookImagePath").value("/image/book/대화의_힘.jpg"));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].bookTitle").value("대화의 힘"));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].author").value("찰스 두히그"));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].rankNum").value(1));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    //랭크 실페 테스트
    @Test
    public void rankBook_test_fail() throws Exception {
        // given
        String category = "소설asdfg";
        // when
        ResultActions actions = mvc.perform(get("/app/book/rank")
                .param("category" ,category)
                .header("Authorization", "Bearer " + jwt));
        // eye
        String respBody = actions.andReturn().getResponse().getContentAsString(); //String으로 변환
        System.out.println("respBody = " + respBody);
        // then
        actions.andExpect(jsonPath("$.status").value(400)); // header 검증
        actions.andExpect(jsonPath("$.msg").value("실패"));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].id").value(1));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].bookImagePath").value("/image/book/대화의_힘.jpg"));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].bookTitle").value("대화의 힘"));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].author").value("찰스 두히그"));
        actions.andExpect(jsonPath("$.data.totalBestSellers[0].rankNum").value(1));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
}

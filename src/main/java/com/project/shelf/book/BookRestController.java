package com.project.shelf.book;


import com.project.shelf._core.util.ApiUtil;
import com.project.shelf.admin.AdminRequestRecord.BookSaveReqDTO;
import com.project.shelf.author.AuthorResponseRecord.SearchPageRespDTO;
import com.project.shelf.author.AuthorService;
import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import com.project.shelf.book.BookResponseRecord.BrandNewRespDTO;
import com.project.shelf.user.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookRestController {
    private static final Logger log = LoggerFactory.getLogger(BookRestController.class);
    private final AuthorService authorService;
    private final BookService bookService;
    private final HttpSession session;


    // Brandnew 페이지
    @GetMapping("/app/book/new")
    public ResponseEntity<?> brandNew(@RequestParam(value = "month", required = true) String registrationMonth) {
        List<BrandNewRespDTO> respDTO = bookService.brandNew(registrationMonth);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    //책 검색 결과페이지
    @GetMapping("/app/book/search")
    public ResponseEntity<?> bookSearch(@RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "author", required = false) String author) {


        BookCategorySearchDTO respDTO = bookService.bookSearch(category, author);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }


    @PostMapping("/api/ebook")
    public ResponseEntity<?> addBook(@ModelAttribute BookSaveReqDTO reqDTO) {
        log.info("이북등록하기 {}", reqDTO);
        try {
            bookService.saveBook(reqDTO);
            return ResponseEntity.ok("eBook 등록 성공!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("eBook 등록 실패");
        }
    }

    // 책 상세보기 페이지
    @GetMapping("/app/book/{bookId}")
    public ResponseEntity<?> bookDetailPage(@PathVariable Integer bookId){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        BookResponse.DetailPageDTO respDTO
                = bookService.bookDetailPage(sessionUser, bookId);

        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    @GetMapping("/categories")
    public List<Book.Category> getCategories() {
        return Arrays.asList(Book.Category.values());
    }
}


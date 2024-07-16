package com.project.shelf.book;


import com.project.shelf._core.util.ApiUtil;
import com.project.shelf.admin.AdminRequestRecord.BookSaveReqDTO;
import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import com.project.shelf.book.BookResponseRecord.BrandNewRespDTO;
import com.project.shelf.book.BookResponseRecord.BookRankRespDTO;
import com.project.shelf.user.SessionUser;
import com.project.shelf.wishlist.WishlistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class BookRestController {

    private final BookService bookService;
    private final WishlistService wishlistService;
    private final HttpSession session;


    // Brandnew 페이지
    @GetMapping("/app/book/new")
    public ResponseEntity<?> brandNew(@RequestParam(value = "month", required = true) String registrationMonth) {
        List<BrandNewRespDTO> respDTO = bookService.brandNew(registrationMonth);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    // 위시리스트가 많은 순으로 베스트 셀러를 조회함
    @GetMapping("/app/book/rank")
    public ResponseEntity<?> getBooksByCategory(@RequestParam(required = false) String category) {
        List<BookRankRespDTO> respDTO = bookService.getBooksByCategory(category);
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
    public ResponseEntity<String> addBook(@ModelAttribute BookSaveReqDTO reqDTO) {
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




}

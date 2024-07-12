package com.project.shelf.book;


import com.project.shelf._core.util.ApiUtil;
import com.project.shelf.author.AuthorResponseRecord.SearchPageRespDTO;
import com.project.shelf.author.AuthorService;
import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookRestController {
    private final AuthorService authorService;
    private  final BookService bookService;


    //책 검색 결과페이지
    @GetMapping("/app/book/search")
    public ResponseEntity<?> bookSearch(@RequestParam("category") String category) {
        List<BookCategorySearchDTO> respDTO =  bookService.bookSearch(category);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }


}

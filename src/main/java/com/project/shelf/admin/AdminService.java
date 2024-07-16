package com.project.shelf.admin;


import com.project.shelf._core.erros.exception.Exception404;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final BookRepository bookRepository;

    //상세보기
    public Book bookDetail(Integer bookId){
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new Exception404("책 정보를 찾을 수 없습니다."));

        return book;

    }

}

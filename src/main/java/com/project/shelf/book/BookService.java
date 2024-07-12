package com.project.shelf.book;


import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    // 카테고리 조회 메서드
    @Transactional
    public List<BookCategorySearchDTO> bookSearch(String category) {
        try {
            Book.Category bookCategory = Book.Category.valueOf(category);

            // 카테고리별 책 수 조회
            Integer bookCount = bookRepository.findByCategoryConut(bookCategory);

            // 카테고리별 책 리스트 조회 및 매핑
            return bookRepository.findByCategory(bookCategory).stream()
                    .map(book -> BookCategorySearchDTO.builder()
                            .BookCount(bookCount)
                            .BookPath(book.getPath())
                            .BookTitle(book.getTitle())
                            .AuthorName(book.getAuthor().getName())
                            .build())
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            // 잘못된 카테고리인 경우 예외 처리
            throw new Exception400("해당 카테고리의 책 정보를 찾을 수 없습니다.");
        }
    }
}

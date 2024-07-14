package com.project.shelf.book;


import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.util.MyFileUtil;
import com.project.shelf.admin.AdminRequestRecord.BookSaveReqDTO;
import com.project.shelf.author.Author;
import com.project.shelf.author.AuthorRepository;
import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

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
                            .bookCount(bookCount)
                            .categoryId(book.getId())
                            .bookPath(book.getPath())
                            .bookTitle(book.getTitle())
                            .authorName(book.getAuthor().getName())
                            .build())
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            // 잘못된 카테고리인 경우 예외 처리
            throw new Exception400("해당 카테고리의 책 정보를 찾을 수 없습니다.");
        }
    }

    public void saveBook(BookSaveReqDTO reqDTO) throws IOException {
        // 저자 정보 가져오기 또는 생성하기
        Author author = authorRepository.findByName(reqDTO.author())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(reqDTO.author());
                    newAuthor.setAuthorIntro(reqDTO.authorIntro());
                    return authorRepository.save(newAuthor);
                });

        // 이미지 파일 저장
        String imagePath = MyFileUtil.write(reqDTO.path(), "/image/");

        // epub 파일 저장
        String epubFilePath = MyFileUtil.write(reqDTO.epubFile(), "/image/epub/");

        // Book 객체 생성 및 저장
        Book book = Book.builder()
                .author(author)
                .title(reqDTO.title())
                .path(imagePath)
                .pageCount(reqDTO.pageCount())
                .bookIntro(reqDTO.bookIntro())
                .contentIntro(reqDTO.contentIntro())
                .category(reqDTO.category())
                .publisher(reqDTO.publisher())
                .epubFile(epubFilePath)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        bookRepository.save(book);
    }
}

package com.project.shelf.book;


import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf._core.util.MyFileUtil;
import com.project.shelf.admin.AdminRequestRecord.BookSaveReqDTO;
import com.project.shelf.author.Author;
import com.project.shelf.author.AuthorRepository;
import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import com.project.shelf.user.SessionUser;
import com.project.shelf.user.User;
import com.project.shelf.user.UserRepository;
import com.project.shelf.wishlist.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;


    public BookCategorySearchDTO bookSearch(String category, String authorName) {
        List<Book> books;

        if (authorName != null && !authorName.isEmpty()) {
            Author author = authorRepository.findByName(authorName)
                    .orElseThrow(() -> new Exception400("저자를 찾을 수 없습니다."));
            books = bookRepository.findByAuthorId(author.getId());
            if (books.isEmpty()) {
                throw new Exception400("저자의 책을 찾을 수 없습니다.");
            }
        } else {
            try {
                Book.Category bookCategory = Book.Category.valueOf(category);
                books = bookRepository.findByCategory(bookCategory);
                if (books.isEmpty()) {
                    throw new Exception400("카테고리에 맞는 책 정보를 찾을 수 없습니다.");
                }
            } catch (IllegalArgumentException e) {
                throw new Exception400("유효하지 않은 카테고리입니다.");
            }
        }

        Integer bookCount = books.size();

        List<BookCategorySearchDTO.Book> bookDTOs = books.stream()
                .map(book -> BookCategorySearchDTO.Book.builder()
                        .bookId(book.getId())
                        .bookTitle(book.getTitle())
                        .bookPath(book.getPath())
                        .author(BookCategorySearchDTO.Book.Author.builder()
                                .authorId(book.getAuthor().getId())
                                .authorName(book.getAuthor().getName())
                                .build())
                        .build())
                .collect(Collectors.toList());

        return BookCategorySearchDTO.builder()
                .bookCount(bookCount)
                .book(bookDTOs)
                .build();
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

    // 책 상세보기 페이지
    public BookResponse.DetailPageDTO bookDetailPage(SessionUser sessionUser, Integer bookId){
//        SessionUser sessionUser = AppJwtUtil.verify(jwt);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception401("책 정보를 찾을 수 없습니다!!"));
        // 사용자 가져오기
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception401("사용자를 찾을 수 없습니다!!"));
        // 저자 가져오기
        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new Exception401("저자를 찾을 수 없습니다!!"));
        book.setAuthor(author);

        // 위시리스트 여부
        Boolean isWish = wishlistRepository.existsByUserAndBook(user, book);

        return new BookResponse.DetailPageDTO(book, isWish);
    }
}

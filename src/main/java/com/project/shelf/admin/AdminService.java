package com.project.shelf.admin;

import com.project.shelf.admin.AdminRequestRecord.BookUpdateReqDTO;
import com.project.shelf.admin.AdminResponseRecord.BookDetailRespDTO;
import com.project.shelf.admin.AdminResponseRecord.BookListRespDTO;
import com.project.shelf._core.erros.exception.Exception404;
import com.project.shelf.admin.AdminResponseRecord.UserListRespDTO;
import com.project.shelf.author.Author;
import com.project.shelf.author.AuthorRepository;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import jakarta.transaction.Transactional;
import com.project.shelf.payment.PaymentRepository;
import com.project.shelf.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BookRepository bookRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    // 회원 관리 페이지
    public UserListRespDTO userList() {
        // 사용자 리스트를 구독자와 비구독자로 구분
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<Boolean, List<UserListRespDTO.UserList>> partitionedUsers = userRepository.findAll().stream()
                .map(user -> {
                    Integer subMonths = paymentRepository.findByUserId(user.getId());
                    return UserListRespDTO.UserList.builder()
                            .userId(user.getId())
                            .email(user.getEmail())
                            .createdAt(user.getCreatedAt().format(formatter))
                            .isSub(user.getStatus())
                            .subMonths(subMonths)
                            .build();
                })
                .collect(Collectors.partitioningBy(UserListRespDTO.UserList::isSub));

        // 구독자 수
        Integer subCount = partitionedUsers.get(true).size();

        // 전체 사용자 리스트
        List<UserListRespDTO.UserList> userList = partitionedUsers.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(UserListRespDTO.UserList::userId).reversed()) // 내림차순 정렬
                .toList();

        return UserListRespDTO.builder()
                .userCount(userList.size())
                .subCount(subCount)
                .userList(userList)
                .build();
    }

    // 책 목록보기
    public BookListRespDTO bookList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<BookListRespDTO.BookDTO> bookDTOList = bookRepository.findAllWithAuthor().stream()
                .map(book -> BookListRespDTO.BookDTO.builder()
                        .bookId(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor().getName())
                        .publisher(book.getPublisher())
                        .createdAt(book.getCreatedAt().format(formatter))
                        .build())
                .collect(Collectors.toList());
        return BookListRespDTO.builder()
                .count(bookDTOList.size())
                .bookDTOList(bookDTOList)
                .build();
    }

    //상세보기
    public BookDetailRespDTO bookDetail(Integer bookId){
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new Exception404("책 정보를 찾을 수 없습니다."));

        BookDetailRespDTO respDTO = BookDetailRespDTO.builder()
                .bookPath(book.getPath())
                .bookTitle(book.getTitle())
                .author(book.getAuthor().getName())
                .publisher(book.getPublisher())
                .category(book.getCategory().name())
                .bookIntro(book.getBookIntro())
                .contentIntro(book.getContentIntro())
                .authorIntro(book.getAuthor().getAuthorIntro())
                .pageCount(book.getPageCount())
                .epubFile(book.getEpubFile())
                .build();

        return respDTO;
    }

    // 책 수정하기
    @Transactional
    public void updateBook(Integer bookId, BookUpdateReqDTO reqDTO){
        //1. 책 정보 조회
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new Exception404("책 정보를 찾을 수 없습니다."));

        //2. 작가 정보 조회 및 생성
        Author author = book.getAuthor();
        if(author == null || !author.getName().equals(reqDTO.author())) {
            author = authorRepository.findByName(reqDTO.author())
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setName(reqDTO.author());
                        newAuthor.setAuthorIntro(reqDTO.authorIntro());
                        return authorRepository.save(newAuthor);
                    });
        }

        //3. 책 정보 업데이트
        book.setTitle(reqDTO.bookTitle());
        book.setAuthor(author);
        book.setPublisher(reqDTO.publisher());
        book.setCategory(Book.Category.valueOf(reqDTO.category().toUpperCase()));
        book.setBookIntro(reqDTO.bookIntro());
        book.setPageCount(reqDTO.pageCount());
        book.setContentIntro(reqDTO.contentIntro());

        bookRepository.save(book);
    }

    //책 사진 업로드
    @Transactional
    public void uploadBookImage(MultipartFile bookImage, Book book){

    }

    ///책 삭제하기
    @Transactional
    public void deleteBook(Integer bookId){
        //1. 책 정보 조회
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception404("책을 찾을 수 없습니다."));

        //2. 책 삭제
        bookRepository.delete(book);
    }
}
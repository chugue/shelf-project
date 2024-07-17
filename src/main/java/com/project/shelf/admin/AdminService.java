package com.project.shelf.admin;

import com.project.shelf.admin.AdminResponseRecord.BookDetailRespDTO;
import com.project.shelf.admin.AdminResponseRecord.BookListRespDTO;
import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf._core.erros.exception.SSRException400;
import com.project.shelf._core.erros.exception.SSRException401;
import com.project.shelf._core.erros.exception.Exception404;
import com.project.shelf.admin.AdminResponseRecord.UserListRespDTO;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import jakarta.transaction.Transactional;
import com.project.shelf.payment.PaymentRepository;
import com.project.shelf.user.UserRepository;
import com.project.shelf.user.SessionUser;
import com.project.shelf.user.UserRepository;
import jakarta.transaction.Transactional;
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
                .build();

        return respDTO;
    }

    // 책 수정하기
    @Transactional
    public void updateBook(Integer bookId){
        //1. 책 정보 조회
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new Exception404("책 정보를 찾을 수 없습니다."));

        //2. 책 정보 업데이트

    }

    //책 사진 업로드
    @Transactional
    public void uploadBookImage(MultipartFile bookImage, Book book){

    }


    @Transactional
    public SessionAdmin login(AdminRequest.LoginDTO reqDTO) {
        Admin admin = adminRepository.findByEmail(reqDTO.getEmail())
                .orElseThrow(() -> new SSRException401("등록되지 않은 이메일 입니다!"));

    return  new SessionAdmin(admin);
    }
}
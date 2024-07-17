package com.project.shelf.admin;

import com.project.shelf.admin.AdminResponseRecord.BookListRespDTO;
import com.project.shelf._core.erros.exception.Exception404;
import com.project.shelf.admin.AdminResponseRecord.UserListRespDTO;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.payment.PaymentRepository;
import com.project.shelf.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

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

    // 상세보기
    public Book bookDetail(Integer bookId){
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new Exception404("책 정보를 찾을 수 없습니다."));
        return book;
    }

}
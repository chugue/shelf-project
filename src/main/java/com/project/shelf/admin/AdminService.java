package com.project.shelf.admin;

import com.project.shelf.admin.AdminResponseRecord.BookListRespDTO;
import com.project.shelf._core.erros.exception.Exception404;
import com.project.shelf.admin.AdminResponseRecord.MonthlySalesPageDTO;
import com.project.shelf.admin.AdminResponseRecord.UserListRespDTO;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.payment.PaymentRepository;
import com.project.shelf.payment.PaymentResponseRecord.MonthlySaleDTO;
import com.project.shelf.user.UserRepository;
import com.project.shelf.user.UserResponseRecord.MonthlyUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final BookRepository bookRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    // 매출 관리 페이지
    public MonthlySalesPageDTO monthlySales() {
        // 월별 매출
        List<MonthlySaleDTO> monthlySaleList = paymentRepository.findSalesByMonth();

        // 월별 가입자
        List<MonthlyUserDTO> monthlyUsersList = userRepository.findUserByMonth();

        // 연도와 월을 기준으로 그룹화하여 병합
        Map<String, MonthlySaleDTO> saleMap = monthlySaleList.stream()
                .collect(Collectors.toMap(s -> s.year() + "-" + s.month(), s -> s));
        Map<String, MonthlyUserDTO> userMap = monthlyUsersList.stream()
                .collect(Collectors.toMap(u -> u.year() + "-" + u.month(), u -> u));

        // 정렬
        List<String> keys = saleMap.keySet().stream()
                .sorted(Comparator.comparing((String key) -> Integer.parseInt(key.split("-")[0])) // 연도 기준 정렬
                        .thenComparing(key -> Integer.parseInt(key.split("-")[1])) // 월 기준 정렬
                        .reversed()) // 최신순 정렬
                .toList();

        // 최신순 정렬된 리스트를 거꾸로 순회하며 누적 값을 계산 + chart Data 저장
        List<MonthlySalesPageDTO.ListDTO> resp = new ArrayList<>();
        List<MonthlySalesPageDTO.ChartDTO> chartDTOS = new ArrayList<>();
        Long cumulativeUsers = 0L;
        Long cumulativeSales = 0L;

        for (int i = keys.size() - 1; i >= 0; i--) {
            String key = keys.get(i);
            MonthlySaleDTO sale = saleMap.get(key);
            MonthlyUserDTO user = userMap.getOrDefault(key, new MonthlyUserDTO(sale.year(), sale.month(), 0L));

            cumulativeUsers += user.userCount();
            cumulativeSales += sale.totalSales();

            // chartData
            chartDTOS.addLast(MonthlySalesPageDTO.ChartDTO.builder()
                    .month(sale.month())
                    .monthlySales(sale.totalSales())
                    .build());

            resp.addFirst(MonthlySalesPageDTO.ListDTO.builder()
                    .year(sale.year())
                    .month(sale.month())
                    .cumulativeTotalUsers(cumulativeUsers)
                    .monthlySubUsers(sale.subUser())
                    .monthlySales(sale.totalSales())
                    .cumulativeTotalSales(cumulativeSales)
                    .build());
        }

        return new MonthlySalesPageDTO(resp, chartDTOS);
    }

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
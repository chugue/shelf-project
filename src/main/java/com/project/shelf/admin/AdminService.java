package com.project.shelf.admin;

import com.project.shelf.admin.AdminRequestRecord.BookUpdateReqDTO;
import com.project.shelf.admin.AdminResponseRecord.BookDetailRespDTO;
import com.project.shelf.admin.AdminResponseRecord.BookListRespDTO;
import com.project.shelf._core.erros.exception.SSRException401;
import com.project.shelf._core.erros.exception.Exception404;
import com.project.shelf.admin.AdminResponseRecord.MonthlySalesPageDTO;
import com.project.shelf.admin.AdminResponseRecord.UserListRespDTO;
import com.project.shelf.author.Author;
import com.project.shelf.author.AuthorRepository;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import jakarta.transaction.Transactional;
import com.project.shelf.payment.PaymentRepository;
import com.project.shelf.payment.PaymentResponseRecord.MonthlySaleDTO;
import com.project.shelf.user.UserRepository;
import com.project.shelf.user.UserResponseRecord.MonthlyUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BookRepository bookRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Book> bookList = bookRepository.findAllWithAuthor();
        List<BookListRespDTO.BookDTO> bookDTOList = IntStream.range(0, bookList.size())
                .mapToObj(i -> BookListRespDTO.BookDTO.builder()
                        .no(bookList.size() - i)
                        .bookId(bookList.get(i).getId())
                        .title(bookList.get(i).getTitle())
                        .author(bookList.get(i).getAuthor().getName())
                        .publisher(bookList.get(i).getPublisher())
                        .registrationDate(bookList.get(i).getRegistrationDate().format(formatter))
                        .path(bookList.get(i).getPath())
                        .build()).collect(Collectors.toList());

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
                .categories(Book.Category.getCategories())
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

    public SessionAdmin login(AdminRequest.LoginDTO reqDTO) {
        Admin admin = adminRepository.findByEmail(reqDTO.getEmail())
                .orElseThrow(() -> new SSRException401("등록되지 않은 이메일 입니다!"));
//        admin = adminRepository.findByPassword(reqDTO.getPassword())
//                .orElseThrow(() -> new SSRException401("비밀번호가 맞지 않습니다."));
    return  new SessionAdmin(admin);
    }

}
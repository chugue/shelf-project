package com.project.shelf.user;

import com.project.shelf._core.enums.Avatar;
import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf._core.util.NaverToken;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.book_history.BookHistoryRepository;
import com.project.shelf.user.UserResponseRecord.MainDTO;
import com.project.shelf.user.UserResponseRecord.MyLibraryResponseDTO;
import com.project.shelf.user.UserResponseRecord.NaverRespDTO;
import com.project.shelf.wishlist.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookHistoryRepository bookHistoryRepository;
    private final NaverToken naverToken;

    //회원가입
    @Transactional
    public User join(UserRequest.JoinDTO reqDTO) {
        Optional<User> userOp = userRepository.findByEmail(reqDTO.getEmail());

        if (userOp.isPresent()) {
            throw new Exception400("중복된 이메일이 존재합니다.");
        }
        User user = userRepository.save(User.builder()
                .email(reqDTO.getEmail())
                .password(reqDTO.getPassword())
                .nickName(reqDTO.getNickName())
                .avatar(Avatar.AVATAR01)
                .status(false)
                .createdAt(LocalDateTime.now())
                .build());
        return user;
    }

    @Transactional
    public LoginRespDTO login(LoginReqDTO reqDTO) {
        User user = userRepository.findByEmail(reqDTO.email())
                .orElseThrow(() -> new Exception400("등록된 정보를 찾을 수 없습니다."));
        log.info("유저 정보: {}", user);

        return LoginRespDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .status(user.getStatus())
                .avatar(user.getAvatar())
                .createdAt(user.getCreatedAt())
                .build();
    }

    //메인페이지
    public MainDTO main(SessionUser sessionUser) {
        //1. 베스트 셀러 정보 DTO 매핑
        List<Book> books = bookRepository.findBooksByHistory();
        I

        //2. 이어보기 정보 DTO 매핑
        List<MainDTO.BookHistoryDTO> bookHistories = bookHistoryRepository.findBookHistoryByUserId(sessionUser.getId()).stream().map(
                bookHistory -> MainDTO.BookHistoryDTO.builder()
                        .userId(sessionUser.getId())
                        .bookId(bookHistory.getBook().getId())
                        .bookImagePath(bookHistory.getBook().getPath())
                        .bookTitle(bookHistory.getBook().getTitle())
                        .pageCount(bookHistory.getBook().getPageCount())
                        .lastReadPage(bookHistory.getLastReadPage())
                        .build()).collect(Collectors.toList());


        LocalDate today = LocalDate.now();

        //3. 주간 베스트 셀러 DTO매핑
        List<MainDTO.WeekBestSellerDTO> weekBestSeller = getWeeklyBestSellers(today).stream().map(
                book -> MainDTO.WeekBestSellerDTO.builder()
                        .id(book.getId())
                        .bookImagePath(book.getPath())
                        .bookTitle(book.getTitle())
                        .author(book.getAuthor().getName())
                        .build()).collect(Collectors.toList());


        //4, 일간 베스트 셀러 정보 DTO 매핑
        Book book = getDailyBestSellers(today);
        MainDTO.DayBestSellerDTO dayBestSeller = MainDTO.DayBestSellerDTO.builder()
                .id(book.getId())
                .bookTitle(book.getTitle())
                .bookImagePath(book.getPath())
                .bookIntro(book.getBookIntro())
                .author(book.getAuthor().getName())
                .build();




        return MainDTO.builder()
                .bestSellerDTOS(bestSeller)
                .bookHistoryDTOS(bookHistories)
                .weekBestSellerDTOS(weekBestSeller)
                .dayBestSellerDTO(dayBestSeller)
                .build();
    }

    //주간 베스트 셀러 날짜 구하는 메서드
    public List<Book> getWeeklyBestSellers(LocalDate date) {
        LocalDateTime startOfWeek = date.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = date.with(DayOfWeek.SUNDAY).atTime(LocalTime.MAX);

        System.out.println(startOfWeek + " 찾아라 " + endOfWeek);
        return bookRepository.findWeekBestSellers(startOfWeek, endOfWeek);
    }


    //일별 베스트셀러 날짜 구하는 메서드
    public Book getDailyBestSellers(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay(); // 하루의 시작 시간
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 하루의 끝 시간
        Pageable pageable = PageRequest.of(0,1);
        Page<Book> page = bookRepository.findTopDayBestSeller(startOfDay,endOfDay,pageable);
        return page.getContent().get(0);
    }

    //네이버 오어스
    public String oauthNaver(String code) {
        RestTemplate restTemplate = new RestTemplate();

        NaverRespDTO naverResponse = naverToken.getNaverToken(code, restTemplate);
        NaverRespDTO.NaverUserDTO naverUser = naverToken.getNaveUser(naverResponse.accessToken(), restTemplate);

        String email = "naver_" + naverUser.response().id();

        User oauthUser = userRepository.findByEmail(email).orElseThrow(() -> new Exception400("사용자 정보를 찾을 수 없습니다."));

        if (oauthUser != null) {
            return AppJwtUtil.create(oauthUser);
        } else {
            User user = User.builder()
                    .password(UUID.randomUUID().toString())
                    .email(naverUser.response().email())
                    .provider("naver")
                    .build();
            User returnUser = userRepository.save(user);
            return AppJwtUtil.create(returnUser);
        }
    }

    // 사용자 마이 페이지
    public UserResponse.MyPageDTO MyPage(SessionUser sessionUser) {
        // 사용자 정보 불러오기 ( 세션 )
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception401("❗로그인 되지 않았습니다❗"));

        return new UserResponse.MyPageDTO(user);
    }

    // 사용자 개인 정보
    public UserResponse.MyInfoDTO MyInfo(SessionUser sessionUser) {
        // 사용자 정보 불러오기 ( 세션 )
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception401("❗로그인 되지 않았습니다❗"));

        return new UserResponse.MyInfoDTO(user);
    }

    // 사용자 정보 수정
    @Transactional
    public UserResponse.UpdateInfoDTO UpdateInfo(SessionUser sessionUser, UserRequest.UpdateInfoDTO reqDTO) {
        // 사용자 정보 불러오기 ( 세션 )
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception401("❗로그인 되지 않았습니다❗"));
        // 사용자 정보 업데이트
        user.setNickName(reqDTO.getNickName());
        user.setPhone(reqDTO.getPhone());
        user.setAddress(reqDTO.getAddress());

        return new UserResponse.UpdateInfoDTO(user);
    }

    //내 서재 페이지
    public MyLibraryResponseDTO myLibrary(SessionUser sessionUser) {
        //1. 이어보기 정보 DTO 매핑
        List<MyLibraryResponseDTO.BookListDTO.HistoryDTO> bookHistories = bookHistoryRepository.findBookHistoryByUserId(sessionUser.getId()).stream().map(
                bookHistory -> MyLibraryResponseDTO.BookListDTO.HistoryDTO.builder()
                        .id(bookHistory.getBook().getId())
                        .imagePath(bookHistory.getBook().getPath())
                        .bookTitle(bookHistory.getBook().getTitle())
                        .pageCount(bookHistory.getBook().getPageCount())
                        .lastReadPage(bookHistory.getLastReadPage())
                        .build()).collect(Collectors.toList());

        //2. 전체 도서 DTO 매핑
        List<MyLibraryResponseDTO.BookListDTO.AllBookDTO> allBook = bookHistoryRepository.findBookListByUserId(sessionUser.getId()).stream().map(
                bookHistory -> MyLibraryResponseDTO.BookListDTO.AllBookDTO.builder()
                        .id(bookHistory.getBook().getId())
                        .bookTitle(bookHistory.getBook().getTitle())
                        .author(bookHistory.getBook().getAuthor().getName())
                        .build()).collect(Collectors.toList());

        //3. 책 목록 DTO 매핑
        List<MyLibraryResponseDTO.BookListDTO> bookList = new ArrayList<>();
        bookList.add(MyLibraryResponseDTO.BookListDTO.builder()
                        .historyList(bookHistories)
                        .allBook(allBook)
                .build());

        //4. 위시리스트 DTO 매핑
        List<MyLibraryResponseDTO.WishListDTO> wishList = wishlistRepository.findWishlistByByUserId(sessionUser.getId()).stream().map(
                wishlist -> MyLibraryResponseDTO.WishListDTO.builder()
                        .id(wishlist.getId())
                        .bookId(wishlist.getBook().getId())
                        .bookTitle(wishlist.getBook().getTitle())
                        .author(wishlist.getBook().getAuthor().getName())
                        .build()).collect(Collectors.toList());

        return MyLibraryResponseDTO.builder()
                .bookList(bookList)
                .wishList(wishList)
                .build();

    }
}

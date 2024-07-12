package com.project.shelf.user;

import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf._core.util.NaverToken;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.book_history.BookHistory;
import com.project.shelf.book_history.BookHistoryRepository;
import com.project.shelf.user.UserResponseRecord.NaverRespDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookHistoryRepository bookHistoryRepository;
    private final NaverToken naverToken;

    //회원가입
    @Transactional
    public User join(UserRequest.JoinDTO reqDTO){
        userRepository.findByEmail(reqDTO.getEmail())
                .ifPresent(existingUser -> {
                    throw new Exception400("중복된 이메일이 존재 합니다.");
                });


        User user = userRepository.save(User.builder()
                        .email(reqDTO.getEmail())
                        .password(reqDTO.getPassword())
                        .nickName(reqDTO.getNickName())
                .build());
        return user;
    }

    @Transactional
    public LoginRespDTO login(LoginReqDTO reqDTO){
        User user = userRepository.findByEmail(reqDTO.email())
                .orElseThrow(() -> new Exception400("등록된 정보를 찾을 수 없습니다."));
        log.info("유저정보", user);

        LoginRespDTO respDTO = LoginRespDTO.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return respDTO;
    }

    //메인페이지
    public void main(SessionUser sessionUser){
        //1. 베스트 셀러 정보 가져오기
        List<Book> bestSeller = bookRepository.findBooksByHistory();

        //2. 이어보기 정보 가져오기
        List<BookHistory> bookHistories = bookHistoryRepository.findBookHistoryByUserId(sessionUser.getId());

        LocalDate today = LocalDate.now();

        //3. 주간 베스트 셀러 정보 가져오기
        List<Book> weekBestSeller = getWeeklyBestSellers(today);

        //4, 일간 베스트 셀러 정보 가져오기
        List<Book> DailyBestSeller = getDailyBestSellers(today);

        //5. DTO 매핑하기


    }

    //주간 베스트 셀러 날짜 구하는 메서드
    public List<Book> getWeeklyBestSellers(LocalDate date) {
        LocalDateTime startOfWeek = date.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = date.with(DayOfWeek.SUNDAY).atTime(LocalTime.MAX);

                System.out.println(startOfWeek + " 찾아라 " + endOfWeek);
        return bookRepository.findWeekBestSellers(startOfWeek, endOfWeek);
    }



    //일별 베스트셀러 날짜 구하는 메서드
    public List<Book> getDailyBestSellers(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay(); // 하루의 시작 시간
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 하루의 끝 시간
        return bookRepository.findDayBestSellers(startOfDay,endOfDay);
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
}

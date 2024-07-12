package com.project.shelf.user;

import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.book_history.BookHistory;
import com.project.shelf.book_history.BookHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookHistoryRepository bookHistoryRepository;

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

    //메인페이지
    public void main(SessionUser sessionUser){
        //1. 베스트 셀러 정보 가져오기
        List<Book> bestSeller = bookRepository.findBooksByHistory();

        //2. 이어보기 정보 가져오기
        List<BookHistory> bookHistories = bookHistoryRepository.findBookHistoryByUserId(sessionUser.getId());

        //3. 주간 베스트 셀러 정보 가져오기
//        List<Book> weekBestSeller = getWeeklyBestSellers();

        //4, 일간 베스트 셀러 정보 가져오기

//        //5. DTO 매핑하기
//        List<UserResponse.MainDTO.BestSellerDTO> bestSellerDTOs = books.stream()
//                .map(book -> new UserResponse.MainDTO.BestSellerDTO(book))
//                .collect(Collectors.toList());
    }

    //주간 베스트 셀러 날짜 구하는 메서드
    public List<Book> getWeeklyBestSellers(LocalDate date) {
        LocalDateTime startOfWeek = date.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = date.with(DayOfWeek.SUNDAY).atTime(LocalTime.MAX);

                System.out.println(startOfWeek + " 찾아라 " + endOfWeek);
        return bookRepository.findWeekBestSellers(startOfWeek, endOfWeek);    }



    //일별 베스트셀러 날짜 구하는 메서드
    public List<Book> getDailyBestSellers(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay(); // 하루의 시작 시간
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 하루의 끝 시간
        return bookRepository.findDayBestSellers(startOfDay,endOfDay);
    }


}

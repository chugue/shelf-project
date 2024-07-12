package com.project.shelf.user;

import com.project.shelf.book.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    //주간 베스트 셀러
    @Test
    public void getWeeklyBestSellers_Test(){
        // given
        LocalDate date = LocalDate.of(2024, 7, 12);

        // when
        List<Book> bestSellers = userService.getWeeklyBestSellers(date);

        // then
        System.out.println(bestSellers.size());
    }



    // 일별 베스트 셀러
    @Test
    public void findDayBestSellers_Test(){
        // given
        LocalDate date =LocalDate.of(2024, 7, 12);

        // when
        List<Book> bestSeller = userService.getDailyBestSellers(date);

        // then
        System.out.println(bestSeller.size());
    }
}

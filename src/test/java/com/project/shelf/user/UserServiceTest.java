package com.project.shelf.user;

import com.project.shelf.book.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    // 일별 베스트 셀러
    @Test
    public void findDayBestSellers_Test(){
        // given
        LocalDate today = LocalDate.now();

        // when
        Book bestSeller = userService.getDailyBestSellers(today);

        // then
        System.out.println("책: "+bestSeller);
    }
}

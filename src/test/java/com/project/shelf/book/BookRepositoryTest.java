package com.project.shelf.book;

import com.project.shelf.user.UserResponseRecord.RankResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findByRegistrationMonth_test(){
        // given
        int year = 2021;
        int month = 7;

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // 해당 월의 마지막날 구하기

        // when
        List<Book> books = bookRepository.findByRegistrationMonth(startDate.atStartOfDay(), endDate.atStartOfDay().with(LocalTime.MAX));

        // eye
        books.forEach(System.out::println);

        // then

    }

    @Test
    public void findBooksByHistory_Test(){
        // given

        // when
        List<Book> bestSeller = bookRepository.findBooksByHistory();

        // then
        bestSeller.forEach(System.out::println);
    }


    @Test
    void findByCategory() {
        List<Book> books = bookRepository.findByCategory(Book.Category.소설);
        books.forEach(book -> System.out.println("book = " + book));
    }

    @Test
    void findBestSellersByCategory_Test(){
        // when
        List<RankResponseDTO.CategoryByBestSellerDTO> bestSeller = bookRepository.findBestSellersByCategory(Book.Category.소설);

        // then
        //bestSeller.forEach(System.out::println);
        System.out.println(bestSeller.size());
        System.out.println(bestSeller.getFirst().id());

    }


}

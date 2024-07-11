package com.project.shelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    //베스트셀러
    @Test
    public void findBooksByHistory_Test(){
        // given

        // when
        List<Object[]> bestSeller = bookRepository.findBooksByHistory();

        // then
        bestSeller.forEach(System.out::println);
    }




}

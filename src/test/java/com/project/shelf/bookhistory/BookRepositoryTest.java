package com.project.shelf.bookhistory;

import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findCurrentAndUpcomingMovies_Test(){
        // given

        // when
        List<Book> bestSeller = bookRepository.findBooksByHistory();

        // then
        bestSeller.forEach(System.out::println);
    }
}

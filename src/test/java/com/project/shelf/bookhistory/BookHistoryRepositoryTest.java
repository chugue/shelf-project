package com.project.shelf.bookhistory;

import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.book_history.BookHistory;
import com.project.shelf.book_history.BookHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class BookHistoryRepositoryTest {

    @Autowired
    private BookHistoryRepository bookHistoryRepository;

    @Test
    public void findCurrentAndUpcomingMovies_Test(){
        // given
        Integer userId = 2;
        // when
        List<BookHistory> bookHistories = bookHistoryRepository.findBookHistoryByUserId(userId);

        // then
        bookHistories.forEach(System.out::println);
    }
}

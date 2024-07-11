package com.project.shelf.book;

import com.project.shelf.author.Author;
import com.project.shelf.book_history.BookHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    //베스트셀러 구하는 쿼리
    @Query("SELECT b FROM BookHistory bh JOIN bh.book b GROUP BY b.id ORDER BY COUNT(bh.id) DESC")
    List<Book> findBooksByHistory();

    //주간별 베스트 셀러 구하는 쿼리


}

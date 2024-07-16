package com.project.shelf.book_history;

import com.project.shelf.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookHistoryRepository extends JpaRepository<BookHistory, Integer> {

    //이어보기 구하는 쿼리
    @Query("SELECT bh FROM BookHistory bh JOIN FETCH bh.book b JOIN FETCH bh.user u WHERE u.id = :userId")
    List<BookHistory> findBookHistoryByUserId(@Param("userId") Integer userId);

    //사용자가 읽은 모든 책 구하는 쿼리
    @Query("select bh from BookHistory bh JOIN FETCH bh.book b JOIN FETCH b.author a JOIN FETCH bh.user u where u.id = :userId ")
    List<BookHistory> findBookListByUserId(@Param("userId") Integer userId);
}

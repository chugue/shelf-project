package com.project.shelf.book_history;

import com.project.shelf.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookHistoryRepository extends JpaRepository<BookHistory, Integer> {

    //이어보기 구하는 쿼리
    @Query("SELECT bh FROM BookHistory bh JOIN FETCH bh.book b JOIN FETCH bh.user u WHERE u.id = :userId")
    List<BookHistory> findBookHistoryByUserId(@Param("userId") Integer userId);

    //주간 베스트 셀러 구하는 쿼리
    @Query("SELECT b, COUNT(h.id) AS readCount FROM BookHistory h JOIN h.book b WHERE h.createdAt >= :startOfWeek and h.createdAt <= :endOfWeek GROUP BY b ORDER BY readCount DESC")
    List<Object[]> findWeekBestSellers(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    //일별 베스트 셀러 구하는 쿼리
    @Query("SELECT b, COUNT(h.id) AS readCount FROM BookHistory h JOIN h.book b WHERE h.createdAt <= :endOfDay GROUP BY b ORDER BY readCount DESC")
    List<Object[]> findDayBestSellers(@Param("endOfDay") LocalDateTime endOfDay);
}

package com.project.shelf.book;

import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    //베스트셀러 구하는 쿼리
    @Query("SELECT b,a FROM BookHistory bh JOIN bh.book b join bh.book.author a GROUP BY b.id ORDER BY COUNT(bh.id) DESC")
    List<Book> findBooksByHistory();

    //주간 베스트 셀러 구하는 쿼리
    @Query("SELECT b,a FROM BookHistory bh JOIN bh.book b join bh.book.author a WHERE bh.createdAt >= :startOfWeek AND bh.createdAt <= :endOfWeek GROUP BY b.id ORDER BY COUNT(bh.id) DESC")
    List<Book> findWeekBestSellers(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    //일별 베스트 셀러 구하는 쿼리
    @Query("SELECT b,a FROM BookHistory bh JOIN bh.book b join bh.book.author a WHERE bh.createdAt >= :startOfDay AND bh.createdAt <= :endOfDay GROUP BY b.id ORDER BY COUNT(bh.id) DESC")
    List<Book> findDayBestSellers(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT b FROM Book b where b.category = :category")
    List<Book> findByCategory(@Param("category") Book.Category category);


    @Query("SELECT count(*) FROM Book b where b.category = :category")
    Integer findByCategoryConut(@Param("category") Book.Category category);


}

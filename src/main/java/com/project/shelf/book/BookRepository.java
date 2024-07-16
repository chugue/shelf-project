package com.project.shelf.book;

import com.project.shelf.author.Author;
import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // 한달 간의 신간 구하는 쿼리
    @Query("SELECT b FROM Book b JOIN FETCH b.author a WHERE b.registrationDate>= :startDate AND b.registrationDate <= :endDate GROUP BY b.id ORDER BY COUNT(b.id) DESC")
    List<Book> findByRegistrationMonth(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 베스트셀러 구하는 쿼리
    @Query("SELECT b,a FROM BookHistory bh JOIN bh.book b join bh.book.author a GROUP BY b.id ORDER BY COUNT(bh.id) DESC")
    List<Book> findBooksByHistory();

    // 주간 베스트 셀러 구하는 쿼리
    @Query("SELECT b,a FROM BookHistory bh JOIN bh.book b join bh.book.author a WHERE bh.createdAt >= :startOfWeek AND bh.createdAt <= :endOfWeek GROUP BY b.id ORDER BY COUNT(bh.id) DESC")
    List<Book> findWeekBestSellers(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    // 일별 베스트 셀러 구하는 쿼리
    @Query("SELECT b FROM BookHistory bh JOIN bh.book b JOIN fetch bh.book.author a WHERE bh.createdAt >= :startOfDay AND bh.createdAt <= :endOfDay GROUP BY b.id ORDER BY COUNT(bh.id) DESC")
    Page<Book> findTopDayBestSeller(@Param("startOfDay") LocalDateTime startOfDay,
                                    @Param("endOfDay") LocalDateTime endOfDay,
                                    Pageable pageable);

    @Query("SELECT b FROM Book b JOIN FETCH b.author a WHERE a.id = :authorId")
    List<Book> findByAuthorId(@Param("authorId") Integer authorId);

    @Query("SELECT b FROM Book b JOIN FETCH b.author a WHERE b.category = :category")
    List<Book> findByCategory(@Param("category") Book.Category category);


    @Query("SELECT count(*) FROM Book b where b.category = :category")
    Integer findByCategoryConut(@Param("category") Book.Category category);

    // 관리자 책 목록 보기
    @Query("SELECT b FROM Book b JOIN FETCH b.author a ORDER BY b.id DESC")
    List<Book> findAllWithAuthor();

    // 관리자 책 상세보기
    @Query("select b, a.name from Book b JOIN FETCH b.author a where b.id =:bookId")
    Optional<Book> findByBookId(@Param("bookId") Integer bookId);
}

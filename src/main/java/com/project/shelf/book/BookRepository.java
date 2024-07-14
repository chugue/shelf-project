package com.project.shelf.book;

import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT count(*) FROM Book b where b.category = :category")
    Integer findByCategoryConut(@Param("category") Book.Category category);

    //베스트셀러 구하는 쿼리
    @Query("SELECT b FROM BookHistory bh JOIN bh.book b GROUP BY b.id ORDER BY COUNT(bh.id) DESC")
    List<Book> findBooksByHistory();

    @Query("SELECT b FROM Book b where b.category = :category")
    List<Book> findByCategory(@Param("category") Book.Category category);




    //주간별 베스트 셀러 구하는 쿼리


}

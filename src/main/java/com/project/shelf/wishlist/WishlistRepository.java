package com.project.shelf.wishlist;

import com.project.shelf.book.Book;
import com.project.shelf.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    // 위시 리스트 인지 아닌지
    Boolean existsByUserAndBook(User user, Book book);

    @Query("SELECT distinct w FROM Wishlist w JOIN FETCH w.book b JOIN FETCH b.author WHERE b.category = :category ORDER BY w.id DESC")
    List<Wishlist> findAllBooksInWishlist(@Param("category") Book.Category category);

    @Query("SELECT distinct w FROM Wishlist w JOIN FETCH w.book b JOIN FETCH b.author ORDER BY b.id DESC")
    List<Wishlist> findAllBooksInWishlist();

    Optional<Wishlist> findByUserIdAndBookId(Integer userId, Integer bookId);

    @Query("select w from Wishlist w JOIN FETCH w.book b JOIN fetch w.book.author a JOIN fetch w.user u where u.id = :userId")
    List<Wishlist> findWishlistByByUserId(Integer userId);
}

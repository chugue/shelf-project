package com.project.shelf.wishlist;

import com.project.shelf.book.Book;
import com.project.shelf.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    // 위시 리스트 인지 아닌지
    Boolean existsByUserAndBook(User user, Book book);


    Optional<Wishlist> findByUserIdAndBookId(Integer userId, Integer bookId);
}

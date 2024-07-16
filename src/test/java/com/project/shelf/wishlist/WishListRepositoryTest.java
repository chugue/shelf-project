package com.project.shelf.wishlist;

import com.project.shelf.book.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WishlistRepositoryTest {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void findAllBooksInWishlist() {
        List<Wishlist> wishlists = wishlistRepository.findAllBooksInWishlist();
        wishlists.forEach(wishlist -> System.out.println("wishlist = " + wishlist));
    }
}
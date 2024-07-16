package com.project.shelf.wishlist;

import com.project.shelf.book_history.BookHistory;
import com.project.shelf.book_history.BookHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class WishListRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    public void findCurrentAndUpcomingMovies_Test(){
        // given
        Integer userId = 1;
        // when
        List<Wishlist> bookHistories = wishlistRepository.findWishlistByByUserId(userId);

        // then
        System.out.println(bookHistories.size());
        System.out.println(bookHistories.getFirst().getBook().getTitle());
    }


}

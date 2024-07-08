package com.project.shelf.wishlist;

import com.project.shelf.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

}

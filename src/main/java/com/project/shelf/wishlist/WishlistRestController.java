package com.project.shelf.wishlist;

import com.project.shelf._core.util.ApiUtil;
import com.project.shelf.user.*;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import com.project.shelf.wishlist.WishlistRequestRecord.WishlistSaveReqDTO;
import com.project.shelf.wishlist.WishlistResponseRecord.BookDetailForWish;
import com.project.shelf.wishlist.WishlistResponseRecord.WishlistSaveRespDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WishlistRestController {
    private final WishlistService wishlistService;

    @GetMapping("/app/books/{bookId}")
    public ResponseEntity<?> getBooks (@PathVariable Integer bookId){
     BookDetailForWish respDTO = wishlistService.getBooks(bookId);
     return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }


    @PostMapping("/app/wishlist/toggle")
    public ResponseEntity<?> toggleWishlist(@RequestBody WishlistSaveReqDTO requestDTO) {
        WishlistSaveRespDTO respDTO = wishlistService.toggleWishlist(requestDTO);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }
//
//    @DeleteMapping("/delete")
//    public void deleteWishlist(@RequestBody WishlistSaveReqDTO requestDTO) {
//        wishlistService.deleteWishlist(requestDTO);
//    }


}

package com.project.shelf.wishlist;

import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf._core.util.NaverToken;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.book_history.BookHistory;
import com.project.shelf.book_history.BookHistoryRepository;
import com.project.shelf.user.SessionUser;
import com.project.shelf.user.User;
import com.project.shelf.user.UserRepository;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import com.project.shelf.user.UserResponseRecord.NaverRespDTO;
import com.project.shelf.wishlist.WishlistRequestRecord.WishlistSaveReqDTO;
import com.project.shelf.wishlist.WishlistResponseRecord.WishlistSaveRespDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    //위시리스트 저장, 삭제
    @Transactional
    public WishlistSaveRespDTO toggleWishlist(WishlistSaveReqDTO requestDTO) {
        Integer userId = requestDTO.userId();
        Integer bookId = requestDTO.bookId();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("책 정보를 찾을 수 없습니다."));

        return wishlistRepository.findByUserIdAndBookId(userId, bookId)
                .map(wishlist -> {
                    wishlistRepository.delete(wishlist);
                    //삭제
                    return WishlistSaveRespDTO.builder()
                            .id(wishlist.getId())
                            .userId(userId)
                            .bookId(bookId)
                            .createdAt(wishlist.getCreatedAt())
                            .build();
                })

                //저장
                .orElseGet(() -> {
                    Wishlist wishlist = Wishlist.builder()
                            .user(user)
                            .book(book)
                            .build();
                    Wishlist savedWishlist = wishlistRepository.save(wishlist);
                    return WishlistSaveRespDTO.builder()
                            .id(savedWishlist.getId())
                            .userId(userId)
                            .bookId(bookId)
                            .createdAt(savedWishlist.getCreatedAt())
                            .build();
                });
    }
}

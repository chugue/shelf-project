package com.project.shelf.wishlist;

import com.project.shelf.user.UserRepository;
import com.project.shelf.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WishlistController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final HttpSession session;


}

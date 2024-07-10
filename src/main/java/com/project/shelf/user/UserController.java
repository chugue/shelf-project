package com.project.shelf.user;

import com.project.shelf._core.util.ApiUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/user/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO reqDTO) {
            User user = userService.join(reqDTO);
            UserResponse.Join respDTO = new UserResponse.Join(user);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    // 사용자 마이페이지
    @GetMapping("/user/my-page")
    public ResponseEntity<?> myPage() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.MyPageDTO respDTO = userService.MyPage(sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    // 사용자 개인정보
    @GetMapping("/user/my-info")
    public ResponseEntity<?> myInfo() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.MyInfoDTO respDTO = userService.MyInfo(sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    // 개인정보 변경하기
    @PostMapping("/user/update-info")
    public ResponseEntity<?> updateInfo(@RequestBody UserRequest.UpdateInfoDTO reqDTO){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.UpdateInfoDTO respDTO = userService.UpdateInfo(sessionUser, reqDTO);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

}

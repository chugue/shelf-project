package com.project.shelf.user;

import com.project.shelf._core.util.ApiUtil;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf._core.util.JwtVO;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import com.project.shelf.user.UserResponseRecord.MainDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final HttpSession session;


    @GetMapping("/oauth/naver/callback")
    public ResponseEntity<?> oauthCallback(@RequestParam("accessToken") String NaverAccessToken) {
        System.out.println("스프링에서 받은 카카오토큰 : " + NaverAccessToken);
        String blogAccessToken = userService.oauthNaver(NaverAccessToken);
        return ResponseEntity.ok().header("Authorization", "Bearer " + blogAccessToken).body(new ApiUtil(null));
    }


    //회원가입 TODO : respDTO를 담는 로직이 service에 들어가 있어야지 SRP를 지킨 코드지
    @PostMapping("/user/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO reqDTO) {
        System.out.println("👉👉👉👉" + reqDTO.toString());
        User user = userService.join(reqDTO);
        UserResponse.Join respDTO = new UserResponse.Join(user);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }


    //메인
    @GetMapping("/app/main")
    public ResponseEntity<?> mainPage() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        MainDTO respDTO = userService.main(sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    //로그인
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDTO reqDTO) {
        log.info("로그인 요청: {}", reqDTO);
        LoginRespDTO respDTO = userService.login(reqDTO);
        String jwt = AppJwtUtil.create(respDTO.toUser());
        return ResponseEntity.ok()
                .header(JwtVO.HEADER, JwtVO.PREFIX + jwt)
                .body(new ApiUtil<>(respDTO));
    }

    // 사용자 마이페이지
    @GetMapping("/api/user/my-page")
    public ResponseEntity<?> myPage() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.MyPageDTO respDTO = userService.MyPage(sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }


    // 사용자 개인정보
    @GetMapping("/api/user/my-info")
    public ResponseEntity<?> myInfo() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.MyInfoDTO respDTO = userService.MyInfo(sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    // 개인정보 변경하기
    @PostMapping("/user/update-info")
    public ResponseEntity<?> updateInfo(@RequestBody UserRequest.UpdateInfoDTO reqDTO) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.UpdateInfoDTO respDTO
                = userService.UpdateInfo(sessionUser, reqDTO);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

//    @GetMapping("/app/user/wishList")
//    public ResponseEntity<?> wishList() {
//        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
//
//    }
}



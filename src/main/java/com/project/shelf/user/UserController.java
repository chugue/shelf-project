package com.project.shelf.user;

import com.project.shelf._core.util.ApiUtil;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final HttpSession session;


    @GetMapping("/oauth/naver/callback")
    public ResponseEntity<?> oauthCallback(@RequestParam("accessToken") String NaverAccessToken){
        System.out.println("스프링에서 받은 카카오토큰 : "+NaverAccessToken);
        String blogAccessToken = userService.oauthNaver(NaverAccessToken);
        return ResponseEntity.ok().header("Authorization", "Bearer "+blogAccessToken).body(new ApiUtil(null));
    }


    //회원가입
    @PostMapping("/user/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO reqDTO) {

        System.out.println("👉👉👉👉" + reqDTO.toString());
            User user = userService.join(reqDTO);
            UserResponse.Join respDTO = new UserResponse.Join(user);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }



    //TODO: 안쓸거면 지우세요~
//    @GetMapping("/")
//    public ResponseEntity<?> mainPage() {
//        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
//        UserResponse.MainDTO respDTO = userService.main(sessionUser);
//    }

    //로그인
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDTO reqDTO) {
        log.info("로그인 컨트롤러",reqDTO);
        LoginRespDTO respDTO = userService.login(reqDTO);
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


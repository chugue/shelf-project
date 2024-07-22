package com.project.shelf.user;

import com.project.shelf._core.util.ApiUtil;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf._core.util.JwtVO;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import com.project.shelf.user.UserResponseRecord.MainDTO;
import com.project.shelf.user.UserResponseRecord.MyLibraryResponseDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final UserService userService;
    private final HttpSession session;


    @GetMapping("/oauth/naver/callback")
    public ResponseEntity<?> oauthCallback(@RequestParam("accessToken") String NaverAccessToken) {
        System.out.println("받은 네이버토큰 : " + NaverAccessToken);
        String shelfAccessToken = userService.oauthNaver(NaverAccessToken);
        return ResponseEntity.ok().header("Authorization", "Bearer " + shelfAccessToken).body(new ApiUtil(null));
    }

    // 회원가입
    @PostMapping("/user/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO reqDTO, Errors errors) {
        System.out.println("👉👉👉👉" + reqDTO.toString());
        User user = userService.join(reqDTO);
        UserResponse.Join respDTO = new UserResponse.Join(user);
        String jwt = AppJwtUtil.create(user);
        return ResponseEntity.ok()
                .header(JwtVO.HEADER, JwtVO.PREFIX + jwt)
                .body(new ApiUtil<>(respDTO));
    }

    // 로그인
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReqDTO reqDTO, Errors errors) {
        log.info("로그인 요청: {}", reqDTO);
        LoginRespDTO respDTO = userService.login(reqDTO);
        String jwt = AppJwtUtil.create(respDTO.toUser());
        return ResponseEntity.ok()
                .header(JwtVO.HEADER, JwtVO.PREFIX + jwt)
                .body(new ApiUtil<>(respDTO));
    }


    // 중복확인 ( email )
    @GetMapping("/user/check-email")
    public ResponseEntity<?> checkEmailDup(@RequestParam("email") String email) {
        boolean emailDuplicate = userService.checkEmailDuplicate(email);
        if (emailDuplicate) {
            return ResponseEntity.ok(new ApiUtil<>(400, "중복된 이메일입니다."));
        } else {
            return ResponseEntity.ok(new ApiUtil<>(200, "중복되지 않은 이메일입니다."));
        }
    }
    // 중복확인 ( nickName )
    @GetMapping("/user/check-nickName")
    public ResponseEntity<?> checkNickNameDup(@RequestParam("nickName") String nickName) {
        boolean nickNameDuplicate = userService.checkNickNameDuplicate(nickName);
        if (nickNameDuplicate) {
            return ResponseEntity.ok(new ApiUtil<>(400, "중복된 닉네임입니다."));
        } else {
            return ResponseEntity.ok(new ApiUtil<>(200, "중복되지 않은 닉네임입니다."));
        }
    }

    // 메인
    @GetMapping("/app/main")
    public ResponseEntity<?> mainPage() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        MainDTO respDTO = userService.main(sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }


    // 마이페이지
    @GetMapping("/app/user/my-page")
    public ResponseEntity<?> myPage() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.MyPageDTO respDTO = userService.MyPage(sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    // 개인정보
    @GetMapping("/app/user/my-info")
    public ResponseEntity<?> myInfo() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.MyInfoDTO respDTO = userService.MyInfo(sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    // 개인정보 변경하기
    @PostMapping("/app/user/update-info")
    public ResponseEntity<?> updateInfo(@Valid @RequestBody UserRequest.UpdateInfoDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.UpdateInfoDTO respDTO
                = userService.UpdateInfo(sessionUser, reqDTO);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    // 내 서재
    @GetMapping("/app/user/my-library")
    public ResponseEntity<?> wishList() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        MyLibraryResponseDTO respDTO = userService.myLibrary(sessionUser);

        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }


}



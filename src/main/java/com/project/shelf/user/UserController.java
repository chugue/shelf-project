package com.project.shelf.user;

import com.project.shelf._core.util.ApiUtil;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {


    private final UserRepository userRepository;
    private final UserService userService;

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

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDTO reqDTO) {
        log.info("로그인 컨트롤러",reqDTO);
        LoginRespDTO respDTO = userService.login(reqDTO);
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

}

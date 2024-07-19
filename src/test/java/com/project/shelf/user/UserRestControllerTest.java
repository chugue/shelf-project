package com.project.shelf.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class UserRestControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    // 회원가입 테스트
    @Test
    public void join_test(){
        // given
        String email    = "matthew@gmail.com";
        String nickName = "matthew";
        String password = "1234";

        UserRequest.JoinDTO reqDTO = new UserRequest.JoinDTO(email, nickName, password);

        System.out.println("요청 DTO : " + reqDTO.toString());

        // when
        userService.join(reqDTO);


        // then
        Optional<User> user = userRepository.findByEmail(email);

    }
}

package com.project.shelf.user;

import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User join(UserRequest.JoinDTO reqDTO){
        Optional<User> userOp = userRepository.findByEmail(reqDTO.getEmail());

        if(userOp.isPresent()){
           throw new Exception400("중복된 이메일이 존재합니다.");
        }
        User user = userRepository.save(User.builder()
                        .email(reqDTO.getEmail())
                        .password(reqDTO.getPassword())
                        .nickName(reqDTO.getNickName())
                .build());
        return user;
    }

    @Transactional
    public LoginRespDTO login(LoginReqDTO reqDTO){
        User user = userRepository.findByEmail(reqDTO.email())
                .orElseThrow(() -> new Exception400("등록된 정보를 찾을 수 없습니다."));

        LoginRespDTO respDTO = LoginRespDTO.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return respDTO;
    }
}

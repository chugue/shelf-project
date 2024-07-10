package com.project.shelf.user;

import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.erros.exception.Exception401;
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

    // 사용자 마이 페이지
    public UserResponse.MyPageDTO MyPage(SessionUser sessionUser){
        // 사용자 정보 불러오기 ( 세션 )
//        User user = userRepository.findById(sessionUser.getId())
        User user = userRepository.findById(1)
                .orElseThrow(() -> new Exception401("❗로그인 되지 않았습니다❗"));

        return new UserResponse.MyPageDTO(user);
    }

    // 사용자 개인 정보
    public UserResponse.MyInfoDTO MyInfo(SessionUser sessionUser) {
        // 사용자 정보 불러오기 ( 세션 )
//        User user = userRepository.findById(sessionUser.getId())
        User user = userRepository.findById(1)
                .orElseThrow(() -> new Exception401("❗로그인 되지 않았습니다❗"));

        return new UserResponse.MyInfoDTO(user);
    }

    // 사용자 정보 수정
    @Transactional
    public UserResponse.UpdateInfoDTO UpdateInfo(SessionUser sessionUser, UserRequest.UpdateInfoDTO reqDTO) {
        // 사용자 정보 불러오기 ( 세션 )
        User user = userRepository.findById(1)
//        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception401("❗로그인 되지 않았습니다❗"));
        // 사용자 정보 업데이트
        user.setNickName(reqDTO.getNickName());
        user.setPhone(reqDTO.getPhone());
        user.setAddress(reqDTO.getAddress());

        return new UserResponse.UpdateInfoDTO(user);
    }
}

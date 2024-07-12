package com.project.shelf.user;

import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf._core.util.NaverToken;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.LoginRespDTO;
import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.book_history.BookHistory;
import com.project.shelf.book_history.BookHistoryRepository;
import com.project.shelf.user.UserResponseRecord.NaverRespDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookHistoryRepository bookHistoryRepository;
    private final NaverToken naverToken;

    //회원가입
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
    public LoginRespDTO login(LoginReqDTO reqDTO) {
        User user = userRepository.findByEmail(reqDTO.email())
                .orElseThrow(() -> new Exception400("등록된 정보를 찾을 수 없습니다."));
        log.info("유저 정보: {}", user);

        return LoginRespDTO.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    //메인페이지
    public void main(SessionUser sessionUser){
        //1. 베스트 셀러 정보 가져오기
        List<Book> books = bookRepository.findBooksByHistory();

        //2. 이어보기 정보 가져오기
        List<BookHistory> bookHistories = bookHistoryRepository.findBookHistoryByUserId(sessionUser.getId());

        // 2. DTO로 매핑하기
        List<UserResponse.MainDTO.BestSellerDTO> bestSellerDTOs = books.stream()
                .map(book -> new UserResponse.MainDTO.BestSellerDTO(book))
                .collect(Collectors.toList());
    }

    //네이버 오어스
    public String oauthNaver(String code) {
        RestTemplate restTemplate = new RestTemplate();

        NaverRespDTO naverResponse = naverToken.getNaverToken(code, restTemplate);
        NaverRespDTO.NaverUserDTO naverUser = naverToken.getNaveUser(naverResponse.accessToken(), restTemplate);

        String email = "naver_" + naverUser.response().id();

        User oauthUser = userRepository.findByEmail(email).orElseThrow(() -> new Exception400("사용자 정보를 찾을 수 없습니다."));

        if (oauthUser != null) {
            return AppJwtUtil.create(oauthUser);
        } else {
            User user = User.builder()
                    .password(UUID.randomUUID().toString())
                    .email(naverUser.response().email())
                    .provider("naver")
                    .build();
            User returnUser = userRepository.save(user);
            return AppJwtUtil.create(returnUser);
        }
    }
}

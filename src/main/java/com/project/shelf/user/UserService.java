package com.project.shelf.user;

import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.book_history.BookHistory;
import com.project.shelf.book_history.BookHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookHistoryRepository bookHistoryRepository;

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
}

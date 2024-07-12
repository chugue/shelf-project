package com.project.shelf.user;

import com.project.shelf.book.Book;
import com.project.shelf.book_history.BookHistory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponse {

    @Data
    public static class Join {
        private Integer id;
        private String email;
        private String password;
        private String nickName;

        public Join(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.nickName = user.getNickName();
        }
    }
}

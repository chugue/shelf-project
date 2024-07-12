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

    // 메인페이지 DTO
    @Data
    public static class MainDTO {
        private List<BestSellerDTO> bestSellers;
        private List<WeekBestSellerDTO>weekSellerDTOS;
        private List<DayBestSellerDTO> dayBestSellerDTOS;
        private List<BookHistoryDTO> bookHistories;

        public MainDTO(List<BestSellerDTO> bestSellers, List<WeekBestSellerDTO> weekSellerDTOS, List<DayBestSellerDTO> dayBestSellerDTOS, List<BookHistoryDTO> bookHistories) {
            this.bestSellers = bestSellers;
            this.weekSellerDTOS = weekSellerDTOS;
            this.dayBestSellerDTOS = dayBestSellerDTOS;
            this.bookHistories = bookHistories;
        }

        //베스트 셀러
        @Data
        public static  class BestSellerDTO {
            private Integer id;
            private String bookFilePath;
            private String bookTitle;
            private String author;
            private LocalDateTime firstRead;
            private LocalDateTime lastRead;

            public BestSellerDTO(Book book, BookHistory bookHistory) {
                this.id = book.getId();
                this.bookFilePath = book.getPath();
                this.bookTitle = book.getTitle();
                this.author = book.getAuthor().getName();
                this.firstRead = bookHistory.getCreatedAt();
                this.lastRead = bookHistory.getUpdatedAt();
            }
        }

        @Data
        public static class WeekBestSellerDTO {
            private Integer id;
            private String bookFilePath;
            private String bookTitle;
            private String author;
            private LocalDateTime firstRead;
            private LocalDateTime lastRead;

            public WeekBestSellerDTO(Book book, BookHistory bookHistory) {
                this.id = book.getId();
                this.bookFilePath = book.getPath();
                this.bookTitle = book.getTitle();
                this.author = book.getAuthor().getName();
                this.firstRead = bookHistory.getCreatedAt();
                this.lastRead = bookHistory.getUpdatedAt();
            }
        }

        @Data
        public static class DayBestSellerDTO {
            private Integer id;
            private String bookTitle;
            private String author;
            private String bookIntro;
            private LocalDateTime firstRead;
            private LocalDateTime lastRead;


            public DayBestSellerDTO(Book book, BookHistory bookHistory) {
                this.id = book.getId();
                this.bookTitle = book.getTitle();
                this.author = book.getAuthor().getName();
                this.bookIntro = book.getBookIntro();
                this.firstRead = bookHistory.getCreatedAt();
                this.lastRead = bookHistory.getUpdatedAt();
            }
        }

        //이어보기
        @Data
        public static class BookHistoryDTO {
            private Integer id;
            private String bookTitle;
            private String pageCount; //총 페이지수
            private String lastReadPage;
            private LocalDateTime firstRead; //처음으로 읽은 날짜
            private LocalDateTime lastRead; // 마지막으로 읽은 날짜

            public BookHistoryDTO(BookHistory bookHistory) {
                this.id = bookHistory.getBook().getId();
                this.bookTitle = bookHistory.getBook().getTitle();
                this.pageCount = bookHistory.getBook().getPageCount();
                this.lastReadPage = bookHistory.getLastReadPage();
                this.firstRead = bookHistory.getCreatedAt();
                this.lastRead = bookHistory.getUpdatedAt();
            }
        }
    }
}

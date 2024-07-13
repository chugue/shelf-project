package com.project.shelf.user;

import com.project.shelf.book.Book;
import com.project.shelf.book_history.BookHistory;
import lombok.Data;

import java.nio.file.Path;
import java.time.LocalDate;
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
        private List<BookHistoryDTO> bookHistories;
        private ScrapDTO scrapDTO;

        public MainDTO(List<BestSellerDTO> bestSellers, List<BookHistoryDTO> bookHistories, ScrapDTO scrapDTO) {
            this.bestSellers = bestSellers;
            this.bookHistories = bookHistories;
            this.scrapDTO = scrapDTO;
        }

        //베스트 셀러
        @Data
        public static  class BestSellerDTO {
            private Integer id;
            private String bookFilePath;
            private String bookTitle;
            private String author;

            public BestSellerDTO(Book book) {
                this.id = book.getId();
                this.bookFilePath = book.getPath();
                this.bookTitle = book.getTitle();
                this.author = book.getAuthor().getName();
            }
        }

        //이어보기
        public class BookHistoryDTO {
            private Integer id;
            private String bookTitle;
            private String pageCount; //총 페이지수
            private String lastReadPage;
            private LocalDate createdAt; //처음으로 읽은 날짜
            private LocalDate updatedAt; // 마지막으로 읽은 날짜


        }

        //좋아요
        public class ScrapDTO {
            private Integer id;
            private String bookTitle;
            private String author;
            private String bookIntro;
        }
    }

    // 마이페이지
    @Data
    public static class MyPageDTO {
        private Integer id;
        private String nickNam;
        //        private String profileIconPath; // 필드가 없음.

        public MyPageDTO(User user) {
            this.id = user.getId();
            this.nickNam = user.getNickName();
        }
    }

    // 개인정보 조회
    @Data
    public static class MyInfoDTO {
        private Integer id;
        private String nickName;
        private String email;
        private String password;
        private String phone;
        // 추가로직
//        private String cardLastNum;   // 카드 번호 마지막 4
//        private String ProfileIconPath;   // 프로필 아이콘 경로

        private String address;

        public MyInfoDTO(User user) {
            this.id = user.getId();
            this.nickName = user.getNickName();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.phone = user.getPhone();
            this.address = user.getAddress();
//            this.cardLastNum = user.getIsSubscribed().get__;
//            this.profileIconPath = user.getProfileIcon.getPath();
        }
    }

    // 개인정보 수정
    @Data
    public static class UpdateInfoDTO {
        private String nickName;
        private String password;
        private String phone;
        private String address;

        // 추가로직
//        private String cardLastNum;       // 카드 번호 마지막 4
//        private Integer ProfileIconId;    // 프로필 아이콘


        public UpdateInfoDTO(User user) {
            this.nickName = user.getNickName();
            this.password = user.getPassword();
            this.phone = user.getPhone();
            this.address = user.getAddress();
//            this.cardLastNum = user.getIsSubscribed().get__;
//            this.profileIconPath = user.ProfileIconId.getId();
        }
    }
}

package com.project.shelf.admin;

import com.project.shelf.admin.AdminResponseRecord.BookDetailRespDTO;
import com.project.shelf.admin.AdminResponseRecord.BookListRespDTO;
import com.project.shelf.admin.AdminResponseRecord.UserListRespDTO;
import com.project.shelf.book.Book;
import com.project.shelf.user.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final HttpSession session;
    private final AdminService adminService;

    // 메인 페이지 - login
    @GetMapping("/")
    public String mainPage(HttpServletRequest request) {
        return "admin/login";
    }

    // 로그인 POST ( 관리자 )
    @PostMapping("/login")
    public String login(AdminRequest.LoginDTO reqDTO) {
        SessionAdmin sessionAdmin = adminService.login(reqDTO);
        session.setAttribute("sessionAdmin", sessionAdmin);
        return "redirect:/admin/sales";
    }
    
    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("sessionAdmin");
        session.invalidate();

        return "redirect:/";
    }
    
    // 매출 확인 페이지
    @GetMapping("/admin/sales")
    public String getSalesPage(HttpServletRequest request) {
        return "admin/sales-dashboard";
    }

    // 회원 관리 페이지
    @GetMapping("/admin/members")
    public String getManageMembers(HttpServletRequest request) {
        UserListRespDTO resp = adminService.userList();
        request.setAttribute("users", resp);
        return "admin/member-management";
    }

    // 책 목록보기 페이지
    @GetMapping("/admin/books")
    public String getManageBooks(HttpServletRequest request) {
        BookListRespDTO resp = adminService.bookList();
        request.setAttribute("books", resp);
        return "admin/book-management";
    }
    
    // 책 등록 페이지
    @GetMapping("/admin/add-book")
    public String getAddBook(HttpServletRequest request) {
        return "admin/add-book";
    }

    // 책 상세보기 페이지
    @GetMapping("/admin/book/{bookId}")
    public String getBookDetail(HttpServletRequest request, @PathVariable Integer bookId) {
        BookDetailRespDTO book = adminService.bookDetail(bookId);

        request.setAttribute("book", book);
        return "admin/book-detail";
    }

    // 책 수정하기 페이지
    @GetMapping("/admin/book-update-form/{bookId}")
    public String getUpdateForm(HttpServletRequest request,@PathVariable Integer bookId) {
        BookDetailRespDTO book = adminService.bookDetail(bookId);

        request.setAttribute("book", book);
        return "admin/book-update";
    }

    // 책 수정하기
    @PostMapping("/admin/book-update/{bookId}")
    public String updateBook(HttpServletRequest request,@PathVariable Integer bookId) {

        return "admin/book-detail";
    }

    // 책 삭제하기
    @DeleteMapping("/admin/book")
    public String deleteBook(HttpServletRequest request) {
        return "admin/sales-dashboard";
    }
}
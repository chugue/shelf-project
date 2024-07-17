package com.project.shelf.admin;

import com.project.shelf.admin.AdminResponseRecord.BookListRespDTO;
import com.project.shelf.admin.AdminResponseRecord.UserListRespDTO;
import com.project.shelf.book.Book;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/")
    public String mainPage(HttpServletRequest request) {
        return "admin/sales-dashboard";
    }

    @GetMapping("/admin/login")
    public String getLoginPage(HttpServletRequest request) {
        return "admin/login";
    }

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

    // 책 목록 페이지
    @GetMapping("/admin/books")
    public String getManageBooks(HttpServletRequest request) {
        BookListRespDTO resp = adminService.bookList();
        request.setAttribute("books", resp);
        return "admin/book-management";
    }

    @GetMapping("/admin/add-book")
    public String getAddBook(HttpServletRequest request) {
        return "admin/add-book";
    }

    // 책 상세보기 페이지
    @GetMapping("/admin/book/{bookId}")
    public String getBookDetail(HttpServletRequest request, @PathVariable Integer bookId) {
        Book book = adminService.bookDetail(bookId);

        request.setAttribute("book", book);
        return "admin/book-detail";
    }

    @GetMapping("/err")
    public String getErrPage(HttpServletRequest request) {
        return "err/err-page";
    }


}
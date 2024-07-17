package com.project.shelf.admin;

import com.project.shelf.admin.AdminRequestRecord.BookUpdateReqDTO;
import com.project.shelf.admin.AdminResponseRecord.BookDetailRespDTO;
import com.project.shelf.admin.AdminResponseRecord.BookListRespDTO;
import com.project.shelf.admin.AdminResponseRecord.UserListRespDTO;
import com.project.shelf.book.Book;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        BookDetailRespDTO book = adminService.bookDetail(bookId);

        request.setAttribute("book", book);
        return "admin/book-detail";
    }

    //책 수정하기 페이지
    @GetMapping("/admin/book-update-form/{bookId}")
    public String getUpdateForm(HttpServletRequest request,@PathVariable Integer bookId) {
        BookDetailRespDTO book = adminService.bookDetail(bookId);

        request.setAttribute("book", book);
        return "admin/book-update";
    }

    //책 수정하기
    @PostMapping("/admin/book-update/{bookId}")
    public String updateBook(@PathVariable Integer bookId, BookUpdateReqDTO bookUpdateReqDTO) {
        adminService.updateBook(bookId, bookUpdateReqDTO);
        return "redirect:/admin/book/"+bookId;
    }

    //책 삭제하기
    @DeleteMapping("/admin/book/delete")
    public String deleteBook() {
        return "admin/sales-dashboard";
    }

    @GetMapping("/err")
    public String getErrPage(HttpServletRequest request) {
        return "err/err-page";
    }


}
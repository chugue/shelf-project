package com.project.shelf.admin;

import com.project.shelf.book.Book;
import com.project.shelf.book.BookService;
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
    private final BookService bookService;

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

    @GetMapping("/admin/members")
    public String getManageMembers(HttpServletRequest request) {
        return "admin/member-management";
    }

    @GetMapping("/admin/books")
    public String getManageBooks(HttpServletRequest request) {
        return "admin/book-management";
    }

    @GetMapping("/admin/add-book")
    public String getAddBook(HttpServletRequest request) {
        return "admin/add-book";
    }

    @GetMapping("/admin/book/{bookId}")
    public String getBookDetail(HttpServletRequest request, @PathVariable Integer bookId) {
        Book book = adminService.bookDetail(bookId);

        request.setAttribute("book", book);
        return "admin/book-detail";
    }


}
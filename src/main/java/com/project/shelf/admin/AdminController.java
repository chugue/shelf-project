package com.project.shelf.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {

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
    @GetMapping("/admin/book-detail")
    public String getBookDetail(HttpServletRequest request) {
        return "admin/book-detail";
    }

}
package com.project.shelf.admin.AdminRequestRecord;


import com.project.shelf.book.Book;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record BookSaveReqDTO(
        String author,
        String title,
        MultipartFile path,
        String pageCount,
        String bookIntro,
        String authorIntro,
        String contentIntro,
        Book.Category category,
        String publisher,
        MultipartFile epubFile
) {
}

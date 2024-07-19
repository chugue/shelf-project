package com.project.shelf.admin.AdminResponseRecord;

import com.project.shelf.book.Book;
import lombok.Builder;

import java.util.List;

@Builder
public record BookDetailRespDTO(
        String bookPath,
        String bookTitle,
        String author,
        String publisher,
        String category,
        List<String> categories,
        String bookIntro,
        String contentIntro,
        String authorIntro,
        Integer pageCount,
        String epubFile
) {
}

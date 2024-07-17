package com.project.shelf.admin.AdminRequestRecord;

public record BookUpdateReqDTO(
        Integer id,
        String bookTitle,
        String author,
        String publisher,
        String category,
        String bookIntro,
        String contentIntro,
        String authorIntro,
        String pageCount
) {
}
